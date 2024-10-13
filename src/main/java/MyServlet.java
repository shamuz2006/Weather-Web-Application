

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import WeatherFinder.*;

/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// Retrieve form data (name attribute value "address" from the form)
        String address = request.getParameter("address");
        Weather report = new Weather();
        report.findWeather(address);
        
        // Set the content type of the response to HTML
        response.setContentType("text/html");
        
        // Write the response 
        response.getWriter().write("<html><link rel=\"stylesheet\" href=\"styles.css\"><body>");
        response.getWriter().write("<h1>Weather Report: " + address + "</h1>");
        response.getWriter().write("<p>" + report + "</p>");
        response.getWriter().write("<p>" + report.getDetailedForecast() + "</p>");
        response.getWriter().write("</body></html>");
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
