<?php
    require '../setup.php';

    $type = "";
    if (array_key_exists('type', $_GET))
    {
        $type = $_GET['type'];
    }

    $data = [
        'data' => [['time' => 'empty', 'date'=> 'empty', 'month' => 'empty', 'temp' => 0]],
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

    else if ($type == "" || $type == "daily")
    {
        $date = new DateTime();
        $yesterday = new DateTime();
        $date->add(new DateInterval('P1D'));
        $sql = 'SELECT * FROM currentweather WHERE time < "' . $date->format('Y-m-d') . ' 00:00:00" 
                AND time > "' . $yesterday->format('Y-m-d') . ' 00:00:00"';

        if ($results = $conn->query($sql)) {
        } else {
            die("Error: " . $sql . "<br>" . $conn->error);
        }

        $info = [];
        if ($results->num_rows > 0) {
            while($row = $results->fetch_assoc()) {
                $time = new DateTime($row['time']);
                $info[] = [
                    'time' => $time->format('h:i a'), 
                    'date' => $time->format('m-d-y'),
                    'month' => $time->format('M'),
                    'temp' => $row['temperatureF']
                ];
            }
        }

        $data['data'] = $info;
    }
    else if ($type == "weekly")
    {
        $dayOfWeek = date('w');

        $sdate = new DateTime();
        $edate = new DateTime();

        if ($dayOfWeek != 0) {
            $sdate->sub(new DateInterval('P' . $dayOfWeek . 'D'));
        }
        $edate->add(new DateInterval('P' . (7 - $dayOfWeek) . 'D'));

        $sql = 'SELECT DAYOFWEEK(`time`) AS day, AVG(temperatureF) AS avgTemp 
                FROM currentweather WHERE time < "' . $edate->format('Y-m-d') . ' 00:00:00" 
                AND time > "' . $sdate->format('Y-m-d') . ' 00:00:00"
                GROUP BY YEAR(`time`), MONTH(`time`), DAY(`time`), DAYOFWEEK(`time`)';

        if ($results = $conn->query($sql)) {
        } else {
            die("Error: " . $sql . "<br>" . $conn->error);
        }

        $days = ["", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
        $info = [];
        if ($results->num_rows > 0) {
            while($row = $results->fetch_assoc()) {
                // $time = new DateTime($row['time']);
                $info[] = [
                    'time' => "", 
                    'date' => $days[$row['day']],
                    'month' => "",
                    'temp' => $row['avgTemp']
                ];
            }
        }

        $data['data'] = $info;
    }
    else if ($type == "monthly")
    {
        $date = new DateTime();
        $sql = 'SELECT DAY(`time`) AS day, AVG(temperatureF) AS avgTemp 
                FROM currentweather WHERE MONTH(time) = ' . $date->format('m') . ' AND YEAR(time) = ' . $date->format('Y') . '
                GROUP BY YEAR(`time`), MONTH(`time`), DAY(`time`)';

        if ($results = $conn->query($sql)) {
        } else {
            die("Error: " . $sql . "<br>" . $conn->error);
        }

        $info = [];
        if ($results->num_rows > 0) {
            while($row = $results->fetch_assoc()) {
                // $time = new DateTime($row['time']);
                $info[] = [
                    'time' => "", 
                    'date' => $row['day'],
                    'month' => "",
                    'temp' => $row['avgTemp']
                ];
            }
        }

        $data['data'] = $info;
    }

    echo(json_encode($data));
?>