/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.servlets;

import edu.pitt.resumecore.Address;
import edu.pitt.resumecore.Media;
import edu.pitt.resumecore.User;
import edu.pitt.servlets.login;
import edu.pitt.utilities.Security;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author ankita
 */
public class processAddMedia extends HttpServlet { 
    
    Media media;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet processAddMedia</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet processAddMedia at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        
       
          HttpSession session = request.getSession();
           String userID= (String) session.getAttribute("userID");
            
           String error="select correct format ";
           session.setAttribute("error", error);
           
          response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // Process only if its multipart content
            // Create a factory for disk-based file items
            File file;
            // We might need to play with the file sizes
            int maxFileSize = 50000 * 1024;
            int maxMemSize = 50000 * 1024;
            ServletContext context = this.getServletContext();
            // Note that this file path refers to a virtual path
            // relative to this servlet.  The uploads directory
            // is part of this project.  The physical path varies 
            // from the virtual path
            
            String filePath = context.getRealPath("/uploads") + "/";
           out.println("Physical folder is located at: " + filePath + "<br />");

            // Verify the content type
            String contentType = request.getContentType();
            // This is very important - make sure that the web form that 
            // uploads the file is actually set to enctype="multipart/form-data"
            // An example of upload form for this project is in index.html
            if ((contentType.indexOf("multipart/form-data") >= 0)) {

                DiskFileItemFactory factory = new DiskFileItemFactory();
                // maximum size that will be stored in memory
                factory.setSizeThreshold(maxMemSize);
                // Location to save data that is larger than maxMemSize.
                factory.setRepository(new File(filePath));

                // Create a new file upload handler
                ServletFileUpload upload = new ServletFileUpload(factory);
                // maximum file size to be uploaded.
                upload.setSizeMax(maxFileSize);
                try {
                    // Parse the request to get file items.
                    List fileItems = upload.parseRequest(request);

                    // Process the uploaded file items
                    Iterator i = fileItems.iterator();
                         while (i.hasNext()) {
                        FileItem fi = (FileItem) i.next();
                        if (!fi.isFormField()) {
                            // Get the uploaded file parameters
                            String fieldName = fi.getFieldName();
                            
                            out.println("field name"+ fieldName);
                            String fileName = fi.getName();
                           
                             out.println("file name"+ fileName); // file name is present
                             
                            boolean isInMemory = fi.isInMemory();
                            long sizeInBytes = fi.getSize();
                            //  out.println("file size"+ sizeInBytes);
                            // Write the file
                            if (fileName.lastIndexOf("\\") >= 0) {
                                file = new File(filePath
                                        + fileName.substring(fileName.lastIndexOf("\\")));
                            } else {
                                file = new File(filePath
                                        + fileName.substring(fileName.lastIndexOf("\\") + 1));
                            }
                            fi.write(file);
                          //  out.println("Uploaded Filename: " + filePath  + fileName + "<br />");
                         String savepath= filePath + fileName;
                         
                         // to check correct format is entered or not 
                         int dotindex= fileName.indexOf(".");
                         if(!(fileName.substring(dotindex).matches(".ogv|.webm|.mp4|.png|.jpeg|.jpg|.gif")))
                         {
                                    response.sendRedirect("./pages/addMedia.jsp");
                         
                         }
                         
               // receives projectID from listProjects.jsp from edit href link , adding in existing project 
               // corresponding constructor is used          
                        if ( session.getAttribute("projectID")!=null) 
                        {
                            Integer projectID= (Integer)session.getAttribute("projectID");
                            
                            media= new Media(projectID,fileName);
                        }
                  // first time when user enters media for project , this constructor is used          
                        else
                        {                      
                                  media = new Media(userID,fileName);
                        }
                        
                        
                           System.out.println("Into the media constructor");
                       // redirected to listProject
                          response.sendRedirect("./pages/listProject.jsp");  
                          
                          // media constructor
                           
                         // response.redirect(listprojects.jsp);
                         
                              processRequest(request, response);
      }
                        
    }
                   
  }  
                
            catch (FileUploadException ex) {       
                    Logger.getLogger(processAddMedia.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(processAddMedia.class.getName()).log(Level.SEVERE, null, ex);
                }  
            }
        }
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



                         
                         // Media media = new Media(userID,fileName);
                         
                         /*
                          if((int)session.getAttribute("projectID")!=)
                           {   
                             media= new Media(projectID,fileName);
                           } 
                         */
                         // second time for edit mode
                         
                         /*
                          if(projectID>0)
                          {
                          media= new Media(projectID,fileName);
                          }  
                        */

                          
       //    int projectID=(int) session.getAttribute("projectID");
            
        // user credentials 2
        
       
         
       // User user = (User) getServletContext().getAttribute ("authenticatedUser");

        // add upload code 
                         
                           // Media media = new Media(userID,fileName);