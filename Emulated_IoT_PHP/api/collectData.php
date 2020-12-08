<?php
    require '../setup.php';

    $type = "";
    if (array_key_exists('type', $_GET))
    {
        $type = $_GET['type'];
    }

    $data = [
        'status' =>
        [
            'code' => 200,
            'message' => 'Success'
        ]
    ];

    $conn = new mysqli(SERVER, USERNAME, PASSWORD, 'cst361clc');

    if ($conn->connect_error)
    {
        $error = 
        [
            'status' => 
            [
                'code' => 500,
                'message' => 'Problem while connecting to the database'
            ]
        ];
        die(json_encode($error));
    }

    $curl = curl_init();

    curl_setopt_array($curl, [
        CURLOPT_URL => "https://community-open-weather-map.p.rapidapi.com/forecast?q=phoenix%2Cus&units=imperial",
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_FOLLOWLOCATION => true,
        CURLOPT_ENCODING => "",
        CURLOPT_MAXREDIRS => 10,
        CURLOPT_TIMEOUT => 30,
        CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
        CURLOPT_CUSTOMREQUEST => "GET",
        CURLOPT_HTTPHEADER => [
            "x-rapidapi-host: community-open-weather-map.p.rapidapi.com",
            "x-rapidapi-key: f1dd775e97msh053c224a9ca6f8ap159903jsn90093964cf54"
        ],
    ]);
    
    $response = curl_exec($curl);
    $err = curl_error($curl);
    
    curl_close($curl);
    
    if ($err) {
        echo "cURL Error #:" . $err;
    } else {
        // echo $response;

        $resultData = json_decode($response, true);

        // echo(print_r($resultData, true));
        $sql = "";
        foreach($resultData['list'] as $item) {
            $date = new DateTime($item['dt_txt']);
            $tempF = $item['main']['temp'];
            $tempC = ($tempF - 32)/1.8;
            $lon = $resultData['city']['coord']['lon'];
            $lat = $resultData['city']['coord']['lat'];
            $location = $resultData['city']['name'];

            $sql = " INSERT INTO currentweather (temperatureF, temperatureC, longitude, latitude, location, time)
                    VALUES (". $tempF .", " . $tempC . ", " . $lon . ", " . $lat . "
                    , '" . $location . "', '" . $date->format('Y-m-d H:i:s') . "');\n";
            if ($conn->query($sql) === TRUE) {
            } else {
                die("Error: " . $sql . "<br>" . $conn->error);
            }
        }
    }

    $conn->close();

    echo(json_encode($data));
?>