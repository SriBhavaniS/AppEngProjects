package com.star.test1;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "HelloAppEngine", urlPatterns = { "/star" })
public class HelloAppEngine extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		int i, j;

		int n = Integer.parseInt(request.getParameter("t1"));

		for (j = 0; j < n; j++) {

			for (i = n; i >= j; i--) {

				response.getWriter().print("*");
			}

			response.getWriter().print("<br>");

		}

	}

}
