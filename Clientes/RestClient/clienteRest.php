<?php


  if (!empty($_REQUEST)) {

    function get($resource, array $parameters = array())

    {

      $query = http_build_query($parameters);

      $curl = curl_init('http://loadbalancer-sd/' . $resource);



      curl_setopt_array($curl, array(CURLOPT_RETURNTRANSFER => true));

      curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, false);



      $answer = curl_exec($curl);

      curl_close($curl);



      return json_decode($answer, true);

    }



    $arrLogin = [

      'nome' => $_REQUEST["usuario"],

      'senha' => $_REQUEST["senha"]

    ];



    $dtInicial = $_REQUEST['dtInicial'];

    $dtFinal = $_REQUEST['dtFinal'];

    $tokenConsulta = $_REQUEST['tokenConsulta'];



    $retorno = get('login', $arrLogin);

    //echo json_encode(['cdRetorno' => 100, 'dsRetorno' => 'Login efetuado!', 'token' => "kFGA�PUHBFAPIbg�dsmgfdoshuADF"]);

    $token = $retorno['token'];



    //$arrRequest = ['token' => $token, 'inicio' => '2018-06-11 16:07:33', 'fim': '2018-06-11 22:07:33'];

    $arrRequest = ['token' => $token, 'inicio' => $dtInicial, 'fim' => $dtFinal];

    $retorno = get('quantidadesUsuariosLoginsValidos', $arrRequest);

    //echo json_encode(['cdRetorno' => 100, 'dsRetorno' => 'quantiodaddasgewbhiwqgfhiouywup!', 'valor' => '20'];)

    $quantidadesUsuariosLoginsValidos =  $retorno['valor'];



    $arrRequest = ['token' => $token, 'inicio' => $dtInicial, 'fim' => $dtFinal];

    $retorno = get('quantidadesTopicosMaisAcessados', $arrRequest);

    //echo json_encode(['cdRetorno' => 100, 'dsRetorno' => 'quantiodaddasgewbhiwqgfhiouywup!', 'valor' => '20'];)

    $quantidadesTopicosMaisAcessados = $retorno['valor'];



    //$arrRequest = ['token' => $token, 'inicio' => '2018-06-11 16:07:33', 'fim': '2018-06-11 22:07:33'];

    $arrRequest = ['token' => $token, 'inicio' => $dtInicial, 'fim' => $dtFinal];

    $retorno = get('quantidadesChatsRealizados', $arrRequest);

    //echo json_encode(['cdRetorno' => 100, 'dsRetorno' => 'quantiodaddasgewbhiwqgfhiouywup!', 'valor' => '20'];)

    $quantidadesChatsRealizados = $retorno['valor'];



    //$arrRequest = ['token' => $token, 'inicio' => '2018-06-11 16:07:33', 'fim': '2018-06-11 22:07:33'];

    $arrRequest = ['token' => $token, 'usuario' => $tokenConsulta, 'inicio' => $dtInicial, 'fim' => $dtFinal];

    $retorno = get('quantidadesChatsRealizadosUsuarios', $arrRequest);

    //echo json_encode(['cdRetorno' => 100, 'dsRetorno' => 'quantiodaddasgewbhiwqgfhiouywup!', 'valor' => '20'];)

    $quantidadesChatsRealizadosUsuarios =  $retorno['valor'];



    $html = <<<HTML

      <br>

      <br>

      <ul class="list-group">

        <li class="list-group-item">FQuantos usuários acessaram a rede: {$quantidadesUsuariosLoginsValidos}</li>

        <li class="list-group-item">Quais os tópicos mais acessados em pesquisas: {$quantidadesTopicosMaisAcessados}</li>

        <li class="list-group-item">Quantos chats foram realizados: {$quantidadesChatsRealizados}</li>

        <li class="list-group-item">Quantos chats foram realizados por usuário: {$quantidadesChatsRealizadosUsuarios}</li>

      </ul>

      <br>



      <a href="clienteRest.php">Filtro</a>

HTML;



  }

  else

  {



    $html = <<<HTML

      <div class="form_main">

        <div class="form">

          <form action="clienteRest.php" method="post" id="form" name="form">

              <input type="text"     required="" placeholder="Usuário"       value="" name="usuario"       class="txt">

              <input type="password" required="" placeholder="Senha"         value="" name="senha"         class="txt">

              <input type="text"     required="" placeholder="Token Usuário" value="" name="tokenConsulta" class="txt">

              <br>

              <br>

              <input type="date"     required="" placeholder="Data Inicial" value="" name="dtInicial" class="txt">

              <input type="date"     required="" placeholder="Data Final"   value="" name="dtFinal"   class="txt">



              <input type="submit" value="submit" name="submit" class="txt2">

          </form>

        </div>

      </div>

HTML;



  }



  echo <<<HTML

     <!DOCTYPE html>

        <html>

        <head>

        <!-- Latest compiled and minified CSS -->

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

        

        <!-- jQuery library -->

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

        

        <!-- Latest compiled JavaScript -->

        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

        

        <style media="screen" type="text/css">

        form_main {

            width: 100%;

        }

        .form_main h4 {

            font-family: roboto;

            font-size: 20px;

            font-weight: 300;

            margin-bottom: 15px;

            margin-top: 20px;

            text-transform: uppercase;

        }

        .heading {

            border-bottom: 1px solid #fcab0e;

            padding-bottom: 9px;

            position: relative;

        }

        .heading span {

            background: #9e6600 none repeat scroll 0 0;

            bottom: -2px;

            height: 3px;

            left: 0;

            position: absolute;

            width: 75px;

        }   

        .form {

            border-radius: 7px;

            padding: 6px;

        }

        .txt[type="text"] {

            border: 1px solid #ccc;

            margin: 10px 0;

            padding: 10px 0 10px 5px;

            width: 100%;

        }

        .txt_3[type="text"] {

            margin: 10px 0 0;

            padding: 10px 0 10px 5px;

            width: 100%;

        }

        .txt2[type="submit"] {

            background: #242424 none repeat scroll 0 0;

            border: 1px solid #4f5c04;

            border-radius: 25px;

            color: #fff;

            font-size: 16px;

            font-style: normal;

            line-height: 35px;

            margin: 10px 0;

            padding: 0;

            text-transform: uppercase;

            width: 30%;

        }

        .txt2:hover {

            background: rgba(0, 0, 0, 0) none repeat scroll 0 0;

            color: #5793ef;

            transition: all 0.5s ease 0s;

        }

        

        </style>

        

        </head>

        

        <body>

          <div class="container">

            <div class="row">

              <div class="col-md-4">

              <h4 class="heading">Cliente Rest</span></h4>

                {$html}

              </div>

            </div>

          </div>

        </body>

        </html>

HTML;
