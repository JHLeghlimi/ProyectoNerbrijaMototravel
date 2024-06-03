/**
 * 
 */
/* AJAX completo. En nuestro caso se hace uso de fetch, que es una función de framework de AJAX. Pero es importante mencionarlo.
	
function llamada {
	
	let xhr = new XMLHttpRequest(); // objeto xhr
	let resultados;
	
	xhr.onreadystatechange = function() { // ejecuta cada estado de la conexion, hay 4 estados
		if (xhr.readyState === 4) { // cuando llegues al 4º estado haz esto
			if (xhr.status === 200) { // simple verificación (ejemplo error 404), el status 200 es que todo ok.
				try {
					resultados = JSON.parse(xhr.responseText);
					// Mediante el objeto JS, parsea ese texto que me va a enviar el servlet de java
					// el responseText, y lo guardes en resultados, que ya tiene todos los resultados en formato JSON.
				} catch (e) {
					// TODO: handle exception
	
				}
			}
		}
	};
	xhr.open("GET", "ListarFotos", false); // llamo al servlet de listar en json
	xhr.setRequestHeader("Content-Type", "application/json"); // opcional para evitar errores
	xhr.send();
	
	pintar(resultados);
	
	// IMPORTANTE: El GET para llamadas y demás, y el POST para formularios y afines. 
	// Es lo común, no es una norma.
	
	// Cuando se ejecute esto, tendremos una variable resultado con todo el formato JSON.
}
*/

function llamada() {
	fetch('GestionRutas?op=1') //llamada al servlet
		.then(response => response.json()) //transformar la respuesta a formato json
		.then(data => pintar(data)) //Al ejecutar data llama a la función pintar y envia el json obtenido.
}


function borrar(idruta) {

	if (confirm("¿Seguro que quieres borrar la ruta?")) {
		fetch('GestionRutas?idruta=' + idruta + '&op=3')
			.then(response => response.json())
			.then(data => pintar(data))
	} else {
		alert("No se ha borrado ningún dato")
	}
}

//Cuando la función llamada se ejecute va a obtener los datos, y llamará a la función pintar para imprimirlos por pantalla.
function pintar(datos) {
    let html = "<table class='tablabonitacss'>";
    html += "<tr><th>Identificador</th><th>Ruta</th><th>Estilo</th><th>Descripción</th><th>Fecha</th><th>Foto</th><th>Editar</th><th>Borrar</th></tr>";
    for (let i = 0; i < datos.length; i++) {
        html += "<tr>";
        html += "<td>" + datos[i].idruta + "</td>";
        html += "<td>" + datos[i].titulo + "</td>";
        html += "<td>" + datos[i].estilo + "</td>";
        html += "<td>" + datos[i].descripcion + "</td>";
        html += "<td>" + datos[i].fecha + "</td>";
        html += "<td>" + datos[i].foto + "</td>";
        html += "<td><a href='publicarRuta.html?idruta=" + datos[i].idruta + "&op=2'>Editar</a></td>";
        html += "<td><a href='javascript:borrar(" + datos[i].idruta + ")'>Borrar</a></td>";
        html += "</tr>";
    }
    html += "</table>";

    document.getElementById("listadoRutas").innerHTML = html;
}


window.onload = function() { //Evento de carga
	llamada();
}




