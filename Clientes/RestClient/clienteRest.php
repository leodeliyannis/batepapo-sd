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
	?>



<!DOCTYPE html>
<html>
<head>
	<title>Cliente REST - BATE PAPO</title>
</head>
	<body style="background-color:powderblue;">>
		<h1 align="center">Bate-Papo por Tópicos</h1>

			 <form>
				  Login:
				  <input type="text" name="login">
				  Dt.Início:
				  <input type="date" name="inicio">
				  Dt.Fim:
				  <input type="date" name="fim">
			</form> 


	<?php
		$retorno = get('login', $arrLogin);
		//echo json_encode(['cdRetorno' => 100, 'dsRetorno' => 'Login efetuado!', 'token' => "kFGAÒPUHBFAPIbgádsmgfdoshuADF"]);
		$token = $retorno['token'];
	?>


		<br>
		<h2>Quantos usuários acessaram a rede?</h2>
			
			<?php
				//$arrRequest = ['token' => $token, 'inicio' => '2018-06-11 16:07:33', 'fim': '2018-06-11 22:07:33'];
				$arrRequest = ['token' => $token, 'inicio' => '11/06/2018 16:07:33', 'fim' => '11/06/2018 22:07:33'];
				$retorno = get('quantidadesUsuariosLoginsValidos', $arrRequest);
				//echo json_encode(['cdRetorno' => 100, 'dsRetorno' => 'quantiodaddasgewbhiwqgfhiouywup!', 'valor' => '20'];)
				echo $retorno['valor'];
			?>	

		<br>
		<h2>Quais os tópicos mais acessados em pesquisas?</h2>
			
			<?php
				$arrRequest = ['token' => $token, 'inicio' => '11/06/2018 16:07:33', 'fim' => '11/06/2018 22:07:33'];
				$retorno = get('quantidadesTopicosMaisAcessados', $arrRequest);
				//echo json_encode(['cdRetorno' => 100, 'dsRetorno' => 'quantiodaddasgewbhiwqgfhiouywup!', 'valor' => '20'];)
				echo $retorno['valor'];
				//------------------------
			?>	

		<br>

		<h2>Quantos chats foram realizados?</h2>
		
			<?php
				//$arrRequest = ['token' => $token, 'inicio' => '2018-06-11 16:07:33', 'fim': '2018-06-11 22:07:33'];
				$arrRequest = ['token' => $token, 'inicio' => '11/06/2018 16:07:33', 'fim' => '11/06/2018 22:07:33'];
				$retorno = get('quantidadesChatsRealizados', $arrRequest);
				//echo json_encode(['cdRetorno' => 100, 'dsRetorno' => 'quantiodaddasgewbhiwqgfhiouywup!', 'valor' => '20'];)
				echo $retorno['valor'];
			?>	

		<br>

		<h2>Quantos chats foram realizados por usuário?</h2>
				
			<?php
				//$arrRequest = ['token' => $token, 'inicio' => '2018-06-11 16:07:33', 'fim': '2018-06-11 22:07:33'];
				$arrRequest = ['token' => $token, 'usuario' => 'OKPUHFuifdLhfhjksgyDASIUY', 'inicio' => '11/06/2018 16:07:33', 'fim' => '11/06/2018 22:07:33'];
				$retorno = get('quantidadesChatsRealizadosUsuarios', $arrRequest);
				//echo json_encode(['cdRetorno' => 100, 'dsRetorno' => 'quantiodaddasgewbhiwqgfhiouywup!', 'valor' => '20'];)
				echo $retorno['valor'];
			?>	

		<br>
	</body>
</html>


