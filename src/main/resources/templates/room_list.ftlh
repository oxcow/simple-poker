<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
  <link href="https://lf9-cdn-tos.bytecdntp.com/cdn/expire-1-M/spectre.css/0.5.9/spectre.min.css" rel="stylesheet">
  <link href="https://lf9-cdn-tos.bytecdntp.com/cdn/expire-1-M/spectre.css/0.5.9/spectre-icons.min.css"
        rel="stylesheet">
  <link href="https://lf26-cdn-tos.bytecdntp.com/cdn/expire-1-M/spectre.css/0.5.9/spectre-exp.min.css" rel="stylesheet">
  <link href='/css/poker.css' rel='stylesheet'>
  <title>Simple Poker Rooms</title>
</head>
<body>

<div class="container">
  <div class="columns bg-dark">
    <div class="column">
      <div class="hero hero-sm p-2">
        <div class="hero-body">
          <h1>Simple Poker</h1>
          <p>Room List</p>
        </div>
      </div>
    </div>
  </div>

  <div class="columns my-2">
    <div class="column col-10 col-mx-auto col-sm-12 col-md-11 col-lg-11 my-2">
      <div class="columns">
        <div class="column">
          <a href="/rooms/create" class="btn btn-primary">Create new room</a>
        </div>
      </div>
    </div>
  </div>

  <div class="divider"></div>

  <div class="columns my-2">
      <#if rooms?size lt 1>
        <div class="column">
          <div class="empty">
            <div class="empty-icon">
              <span class="ec ec-busts-in-silhouette ec_f_lg"></span>
            </div>
            <p class="empty-title h5">No room is exist.</p>
            <p class="empty-subtitle">Click the button to create a new room.</p>
            <div class="empty-action">
              <a href="/rooms/create" class="btn btn-link">Create new room</a>
            </div>
          </div>
        </div>
      <#else>
        <div class="column col-10 col-mx-auto col-sm-12 col-md-11 col-lg-11 my-2">

          <table class="table p-0 m-0">
            <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Type</th>
            </tr>
            </thead>
            <tbody>
            <#list rooms as room>
              <tr class="active">
                <td>${room.roomNo}</td>
                <td>
                    <#if room.type != 'PRIVATE'>
                      <a href="/rooms/${room.roomNo}" target="_blank">${room.name}</a>
                    <#else>
                        ${room.name}
                    </#if>
                </td>
                <td>${room.type}</td>
              </tr>
            </#list>
            </tbody>
          </table>
        </div>
      </#if>
  </div>
</div>
</body>
</html>