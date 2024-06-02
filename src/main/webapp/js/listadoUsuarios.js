/**
 * explicaciones en listadoRutas.js
 */

function llamada() {
	fetch('GestionUsuarios?op=1')
		.then(response => response.json())
		.then(data => pintar(data))
}

function borrar(iduser) {

	if (confirm("¿Seguro que quieres borrar?")) {
		fetch('GestionUsuarios?iduser='+ iduser+'&op=3')
			.then(response => response.json())
			.then(data => pintar(data))
	} else {
		alert("No se ha borrado ningún dato")
	}
}	

function pintar(datos) {

	let html = "<table class=tablabonitacss>";
	html += "<tr><th>Identificador</th><th>Nombre</th><th>Nombre de Usuario</th><th>Email</th><th>  </th><th>  </th></tr>"
	for (let i = 0; i < datos.length; i++) {
		html += "<tr>";
		html += "<td>" + datos[i].iduser + "</td>";
		html += "<td>" + datos[i].nombre + "</td>";
		html += "<td>" + datos[i].username + "</td>";
		html += "<td>" + datos[i].email + "</td>";
		html += "<td><a href='signin.html?iduser="+datos[i].iduser+"&op=2'>Editar</a></td><td><a href='javascript:borrar("+datos[i].iduser+")'>Borrar</a></td>";
		html += "</tr>";
	}
	html += "</table>";

	document.getElementById("listarUsuarios").innerHTML = html;

}

function busquedaPorTipo(tipoUsuario) {
	
	fetch('GestionUsuarios?op=4&tipoUsuario='+tipoUsuario)
		.then(response => response.json())
		.then(data => pintar(data)) 
}

/*
const selectUsuario = document.getElementById('tipoUsuario');
selectUsuario.addEventListener('change', busquedaPorTipo(this.value));
*/

window.onload = function() {
	llamada();
}



