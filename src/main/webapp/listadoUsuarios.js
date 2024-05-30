/**
 * explicaciones en listadoRutas.js
 */

        function llamada() {
            fetch('GestionUsuarios') 
            .then(response => response.json()) 
            .then(data => pintar(data))
        }

        function pintar(datos){

            let html = "<table class=tablabonitacss>";
            html += "<tr><th>Identificador</th><th>Nombre</th><th>Nombre de Usuario</th><th>Email</th></tr>"
            for(let i=0; i<datos.length; i++) {

                html +=	"<tr>";
                html += "<td>" + datos[i].iduser + "</td>";
                html += "<td>" + datos[i].nombre + "</td>";
                html +=	"<td>" + datos[i].username + "</td>";
                html +=	"<td>" + datos[i].email + "</td>";
                html += "</tr>"; 

            }
            html += "</table>";

            document.getElementById("listarUsuarios").innerHTML = html;

        }
        
        window.onload = function() {
            llamada();
        }



