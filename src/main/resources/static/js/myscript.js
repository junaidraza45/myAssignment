$(document).ready(function() {
   $.get( "http://localhost:8080/v1/bus/topbuslines", function( data ) {
        for (var i = 0; i < data.length; i++) {
          //console.log(data[i]["busNumber"] + " " + data[i]["stopsCount"] + " " + data[i]["stopnames"]);
          var newRowContent = "<tr><td> "+data[i]["srno"] +
                              "</td><td>"+ data[i]["busNumber"] +
                              "</td><td>"+ data[i]["stopsCount"] +
                              "</td><td>"+ data[i]["stopnames"] +"</td></tr>";
           $('#bussdetails').append(newRowContent);
        }

   });
});