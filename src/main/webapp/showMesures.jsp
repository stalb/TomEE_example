<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Affichage liste de Mesures</title>
		<link href="default.css" rel="stylesheet" type="text/css" >
	</head>
	<body>
		<table>
			<tr><th>Id</th><th>Pièce</th><th>Température</th><th>Date</th></tr>
			<c:forEach items="${mesures}" var="mesure" >
			<tr>
				<td>${ mesure.id }</td><td>${ mesure.piece }</td>
				<td><fmt:formatNumber pattern="#0.0" value="${ mesure.temp }" /></td>
				<td><fmt:formatDate value="${ mesure.dateMesure }" pattern="dd/MM/yyyy HH:mm:ss" /></td>
			</tr>
			</c:forEach>
		</table>
		<c:choose>
		<c:when test="${not empty param.piece}">
 	    <a href="index.jsp?piece=${param.piece}"  >revenir à la page d'accueil</a>
 	    </c:when>
 	    <c:otherwise>
 	    <a href="index.jsp"  >revenir à la page d'accueil</a>
 	    </c:otherwise>
 	    </c:choose>
	</body>
</html>