<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
  <link href="/css/spectre.min.css" rel="stylesheet">
  <link href="/css/spectre-icons.min.css" rel="stylesheet">
  <link href="/css/spectre-exp.min.css" rel="stylesheet">
  <link href='/css/poker.css' rel='stylesheet'>
  <title>${room.name} information</title>
</head>
<body>
<div class="container">
  <div class="columns bg-dark">
    <div class="column">
      <div class="hero hero-sm p-2">
        <div class="hero-body">
          <h1>Simple Poker</h1>
          <p>${room.name} Information</p>
        </div>
      </div>
    </div>
  </div>
  <div class="columns my-2">
    <div class="column col-10 col-mx-auto col-sm-12 col-md-11 col-lg-11 my-2">
      <form action="/pokers/room/${room.roomNo}" method="POST">
        <div class="columns">
          <div class="column col-5 col-sm-7">
            <input class="form-input is-success" type="text"
                   name="name"
                   placeholder="Input your name" required>
          </div>
          <div class="column">
            <button type="submit" class="btn btn-primary">进入房间</button>
          </div>
        </div>
      </form>
    </div>
  </div>

  <div class="divider"></div>

  <div class="columns my-2">
    <div class="column col-10 col-mx-auto col-sm-12 col-md-11 col-lg-11 my-2">

      <table class="table p-0 m-0">
        <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Role</th>
          <th>Events</th>
        </tr>
        </thead>
        <tbody>
        <#list pokers as poker>
          <tr class="active">
            <td>${poker.id}</td>
            <td>${poker.name}</td>
            <td>${poker.role}</td>
            <td>
              <a href="/pokers/${poker.id}/room/${room.roomNo}" target="_blank">进入房间(Only for test)</a>
            </td>
          </tr>
        </#list>
        </tbody>
      </table>
    </div>
  </div>
</div>
</body>
</html>