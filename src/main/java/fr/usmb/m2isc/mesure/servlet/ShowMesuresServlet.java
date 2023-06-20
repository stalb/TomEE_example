package fr.usmb.m2isc.mesure.servlet;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.usmb.m2isc.mesure.ejb.MesureEJB;
import fr.usmb.m2isc.mesure.jpa.Mesure;

/**
 * Servlet implementation class AddMesureServlet
 */
@WebServlet("/ShowMesuresServlet")
public class ShowMesuresServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// injection de la reference de l'ejb
	@EJB
	private MesureEJB ejb;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowMesuresServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// recuperation et parsing des parametres de la requete
		String piece = request.getParameter("piece");
		// appel de l'ejb
		List<Mesure> l = ejb.findMesures(piece);		
		// ajout de la liste de mesures dans la requete
		request.setAttribute("mesures",l);
		// transfert a la JSP d'affichage
		request.getRequestDispatcher("/showMesures.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
