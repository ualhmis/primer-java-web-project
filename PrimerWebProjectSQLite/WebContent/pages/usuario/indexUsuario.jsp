<%@ page language="java" import="java.util.*, org.is.webApp.*" %>

<jsp:useBean id="listaUsuarios" class="org.is.webApp.ListaUsuarios" scope="session" />

<html>
<head>
<title>Usuarios</title>
</head>
<body bgcolor="#F0F9FF">
    <h1><center>Gestión de Usuarios</center></h1>

<%
		ArrayList<Usuario> usuariosLeidos = listaUsuarios.getListaUsuarios();
%>
Total usuarios: <%= usuariosLeidos.size() %>

<%            
            if (listaUsuarios.getListaUsuarios()!= null) {
                if (listaUsuarios.getListaUsuarios().size () > 0) {
%>
    <table align="center" cellpadding="2" cellspacing="2" border="1" width="60%" bgcolor="#F7F7F7">
        <tr>
        	<th>Usuario</th>
        	<th>Contraseña</th>
        	<th>Fecha de Creación</th>
        </tr>
<%
        			for (int i = 0; i < listaUsuarios.getListaUsuarios().size(); i++) {
                        Usuario usuario= listaUsuarios.getListaUsuarios().get(i);
%>
        <tr>
        	<td><%= usuario.getUsername () %> </td>
        	<td><%= usuario.getPassword () %> </td>
        	<td><%= usuario.getStringFechaCreacion () %> </td>
		</tr>
<%
                    }
                }
            }
%>        

	</table>
    <br>
    <hr>
    <center>
    	<b><a href="nuevoUsuario.jsp">Crear nuevo Usuario</a></b><br>
        <b><a href="../../index.jsp">Volver a la Pagina Principal</a></b>
    </center>

</body>
</html>
</body>
</html>