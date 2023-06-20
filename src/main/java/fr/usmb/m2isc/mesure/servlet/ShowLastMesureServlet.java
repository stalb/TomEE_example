package fr.usmb.m2isc.mesure.servlet;

import java.io.IOException;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import fr.usmb.m2isc.mesure.ejb.MesureEJB;
import fr.usmb.m2isc.mesure.jpa.Mesure;

/**
 * Servlet implementation class AddMesureServlet
 */
@WebServlet("/ShowLastMesureServlet")
public class ShowLastMesureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// injection de la reference de l'ejb
	@EJB
	private MesureEJB ejb;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowLastMesureServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// recuperation et parsing des parametres de la requete
		String piece = request.getParameter("piece");
		// appel de l'ejb
		Mesure m = ejb.findLastMesure(piece);		
		// ajout de la mesure dans la requete
		request.setAttribute("mesure",m);
		// transfert a la JSP d'affichage
		request.getRequestDispatcher("/showMesure.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
