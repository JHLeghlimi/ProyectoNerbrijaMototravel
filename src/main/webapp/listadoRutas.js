/**
 * 
 */

        function llamada() {
            fetch('GestionRutas') //llamada al servlet
            .then(response => response.json()) //transformar la respuesta a formato json
            .then(data => pintar(data)) //Al ejecutar data llama a la función pintar y envia el json obtenido.
        }

        //Cuando la función llamada se ejecute va a obtener los datos, y llamará a la función pintar para imprimirlos por pantalla.
        function pintar(datos){

            let html = "<table class=tablabonitacss>";
            html += "<tr><th>Identificador</th><th>Ruta</th><th>Estilo</th><th>Descripción</th><th>Fecha</th><th>Foto</th></tr>"
            for(let i=0; i<datos.length; i++) {

                html +=	"<tr>";
                html += "<td>" + datos[i].idruta + "</td>";
                html += "<td>" + datos[i].titulo + "</td>";
                html +=	"<td>" + datos[i].estilo + "</td>";
                html +=	"<td>" + datos[i].descripción + "</td>";
                html +=	"<td>" + datos[i].fecha + "</td>";
                html +=	"<td>" + datos[i].foto + "</td>";
                html += "</tr>"; 

            }
            html += "</table>";

            document.getElementById("listadoRutas").innerHTML = html; //método innerHTML, busca el id que se llama listado (en un div)

        }
        
        window.onload = function() { //Evento de carga
            llamada();
        }




 