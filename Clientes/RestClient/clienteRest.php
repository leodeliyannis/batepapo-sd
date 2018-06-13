<?php

public function get($resource, array $parameters = array())
{
    // the query string
    $query = http_build_query($parameters);

    // construct curl resource
    $curl = curl_init('http://loadbalancer-sd/' . $resource);

    // additional options
    curl_setopt_array($curl, array(CURLOPT_RETURNTRANSFER => true));
    curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, false);

    $answer = curl_exec($curl);
    curl_close($curl);

    return json_decode($answer, true);
}

$arrLogin = ['nome' => 'kogler', 'senha' => 'testesenha'];

//------------------------------------------login
$retorno = get('login', $arrLogin);
//echo json_encode(['cdRetorno' => 100, 'dsRetorno' => 'Login efetuado!', 'token' => "kFGAÒPUHBFAPIbgádsmgfdoshuADF"]);
$token = $retorno['token'];

//------------------------------------------quantidadesUsuariosLoginsValidos
//$arrRequest = ['token' => $token, 'inicio' => '2018-06-11 16:07:33', 'fim': '2018-06-11 22:07:33'];
$arrRequest = ['token' => $token, 'inicio' => '11/06/2018 16:07:33', 'fim' => '11/06/2018 22:07:33'];
$retorno = get('quantidadesUsuariosLoginsValidos', $arrRequest);
//echo json_encode(['cdRetorno' => 100, 'dsRetorno' => 'quantiodaddasgewbhiwqgfhiouywup!', 'valor' => '20'];)

echo $retorno['valor'];

//------------------------------------------quantidadesTopicosMaisAcessados
//$arrRequest = ['token' => $token, 'inicio' => '2018-06-11 16:07:33', 'fim': '2018-06-11 22:07:33'];
$arrRequest = ['token' => $token, 'inicio' => '11/06/2018 16:07:33', 'fim' => '11/06/2018 22:07:33'];
$retorno = get('quantidadesTopicosMaisAcessados', $arrRequest);
//echo json_encode(['cdRetorno' => 100, 'dsRetorno' => 'quantiodaddasgewbhiwqgfhiouywup!', 'valor' => '20'];)

echo $retorno['valor'];

//------------------------------------------quantidadesChatsRealizados
//$arrRequest = ['token' => $token, 'inicio' => '2018-06-11 16:07:33', 'fim': '2018-06-11 22:07:33'];
$arrRequest = ['token' => $token, 'inicio' => '11/06/2018 16:07:33', 'fim' => '11/06/2018 22:07:33'];
$retorno = get('quantidadesChatsRealizados', $arrRequest);
//echo json_encode(['cdRetorno' => 100, 'dsRetorno' => 'quantiodaddasgewbhiwqgfhiouywup!', 'valor' => '20'];)

echo $retorno['valor'];

//------------------------------------------quantidadesChatsRealizadosUsuarios
//$arrRequest = ['token' => $token, 'inicio' => '2018-06-11 16:07:33', 'fim': '2018-06-11 22:07:33'];
$arrRequest = ['token' => $token, 'usuario' => 'OKPUHFuifdLhfhjksgyDASIUY', 'inicio' => '11/06/2018 16:07:33', 'fim' => '11/06/2018 22:07:33'];
$retorno = get('quantidadesChatsRealizadosUsuarios', $arrRequest);
//echo json_encode(['cdRetorno' => 100, 'dsRetorno' => 'quantiodaddasgewbhiwqgfhiouywup!', 'valor' => '20'];)

echo $retorno['valor'];