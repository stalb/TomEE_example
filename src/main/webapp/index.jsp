<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>   
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Accueil</title>
	</head>
	<body>
	  <h2>Ajouter une mesure : </h2>
	  <form method="get" action="AddMesureServlet">
		temp�rature : <input name="temp"  type="text"><br>
		capteur : <input name="piece"  type="text" value="${param.piece }" > 
		<input type="submit" name="valider"  value="OK">
	  </form>
	  
	  <h2>Afficher des mesures : </h2>
		<form method="get" action="ShowMesureServlet">
			Afficher la mesure avec l'id : <input name="id"  type="text" value="${param.id }">
			<input type="submit" name="valider"  value="OK">
		</form>
		<form method="get" action="ShowMesuresServlet">
			Afficher les mesures d'une pi�ce : <input name="piece"  type="text" value="${param.piece }" >
			<input type="submit" name="valider"  value="OK">
		</form>
		<form method="get" action="ShowLastMesureServlet">
			Afficher la derni�re mesure d'une pi�ce : <input name="piece"  type="text" value="${param.piece }" >
			<input type="submit" name="valider"  value="OK">
		</form>
		<form method="get" action="ShowLastMesuresServlet">
			Afficher la derni�re mesure de chaque pi�ce : 
			<input type="submit" name="valider"  value="OK">
		</form>
	</body>
</html>