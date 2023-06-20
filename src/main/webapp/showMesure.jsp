<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Affichage Mesure</title>
	</head>
	<body>
	  <p>
		id : ${ mesure.id } <br>
		pièce : ${ mesure.piece } <br>
		temp : <fmt:formatNumber pattern="#0.0">${ mesure.temp }</fmt:formatNumber> <br>
		date : <fmt:formatDate value="${ mesure.dateMesure }" pattern="dd/MM/yyyy HH:mm:ss" /><br>
	  </p>
	  <a href="index.jsp?piece=${mesure.piece}&id=${mesure.id}"  >revenir à la page d'accueil</a>
	</body>
</html>