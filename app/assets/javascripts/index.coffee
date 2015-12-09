$ ->
  $.get "/employees", (employees) ->
    $.each employees, (index, employee) ->
      $("#employees").find('tbody:last')
      .append($("<tr>")
      .append($("<td>").append(employee.id))
      .append($("<td>").append(employee.name))
      .append($("<td>").append(employee.salary))
      .append($("<td>").append(
        $("<button/>").text("Delete").click ->
          $.post "/delete-employee/".concat(employee.id)
          location.reload(true)
        ))
      )