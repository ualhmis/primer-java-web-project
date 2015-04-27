<%@ page language="java" import="java.util.*, org.is.webApp.*" %>

<jsp:useBean id="listaUsuarios" class="org.is.webApp.ListaUsuarios" scope="session" />


<html>
    <head>
        <title>Insertando Usuario</title>
    </head>
    <body bgcolor="#F0F9FF">
        <h1><center>Insertando un nuevo Alumno</center></h1>
        <table align="center" cellpadding="2" cellspacing="2" border="1" width="60%" bgcolor="#F7F7F7">
        	<tr>
        	<th>Nombre de Usuario</th>
			<td><%= request.getParameter ("username") %></td>
        	</tr>
        	<tr>
        	<th>Contraseña</th>
			<td>
			<% for (int i=0;i<request.getParameter ("password").length();i++){
			%>*<%} %></td>
        	</tr>
        </table>
        
        
<%
        int codeError = 0; //no hay error
        String username = request.getParameter ("username");
        if ((username.equals("")) || (username.length()>30) ||
        	(username.contains(" ")) || (username.contains("|")) || (username.contains("@")) || (username.contains("%")))
        		codeError = 1; // Nombre de usuario vacio o demasiado largo o contiene espacios en blanco.
        	else if (!request.getParameter ("password").equals(request.getParameter("password2")))
				codeError = 2; // las contraseñas no coinciden
            
		switch (codeError) {
		 	case 1: 
%>
                <center>
                    <h2><font color="#cc0000">Nombre de usuario vacio, demasiado largo (máximo 30 caracteres) o contiene caracteres no permitidos (espacios en blanco, |@%)</font></h2>
                </center>
<%
			break;
			case 2: 
%>
                <center>
                    <h2><font color="#cc0000">Las contraseñas no coiciden. Vuelva a intentarlo</font></h2>
                </center>

<%
			break;
			case 3: 
%>
                <center>
                    <h2><font color="#cc0000">No se ha podido insertar el registro</font></h2>
                </center>
<%
			break;
			default: 
				Usuario u = new Usuario (
						request.getParameter ("username"),
						request.getParameter ("password"));
				int insercionCorrecta = listaUsuarios.insertarUsuario(u); 
				if (insercionCorrecta == 1){
%>
                <center>
                    <h2><font color="#00cc00">El nuevo usuario ha sido creado</font></h2>
                </center>

<%				}else{ 
%>
				<center>
				    <h2><font color="#cc0000">El nombre de usuario ya existe. Escriba otro.</font></h2>
				</center>
<%             }
		}
%>
        <hr>
        <center>
            <b><a href="../../index.jsp">Volver a la Pagina Principal</a></b>
        </center>

    </body>
</html>