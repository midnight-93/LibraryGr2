/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.librarygr2.servlets;

import com.library.librarygr2.beans.Role;
import com.library.librarygr2.beans.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "UserLoginServlet", urlPatterns = {"/user-login"})
public class UserLoginServlet extends HttpServlet {
    
    HashMap<String, String> users;
    String username;
    String password;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        this.username = request.getParameter("username");
        this.password = request.getParameter("password");
        
        
        if(checkUser(username, password)){
            User user = createUser(username, password);
            saveUserToContext(user, request.getServletContext());
            saveUserToSession(user, request.getSession());
            request.getRequestDispatcher("/dashboard").forward(request, response);
        }
        else{
            request.getRequestDispatcher("/login-failed.html").forward(request, response);
        }
    }
    
    @Override
    public void init(){
        users = new HashMap<>();
        
        users.put("1","1");
        users.put("12","12");
        users.put("123", "123");
    }
    
    private boolean checkUser(String username, String password){
        if(users.containsKey(username)){
            return (password.equals(users.get(username)));
        }
        return false;
    }
    
    private User createUser(String username, String password) {
        return new User(username, password);
    }
    
    private void saveUserToContext(User user, ServletContext context) {
        context.setAttribute("loggedUser", user);
        System.out.println("Dodano do usera kontekstu");
    }
    
    private void saveUserToSession(User user, HttpSession session) {
        session.setAttribute("loggedUser", user);
        System.out.println("Dodano do usera sesji");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
