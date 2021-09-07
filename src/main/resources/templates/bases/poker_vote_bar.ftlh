<div class="columns my-2">
  <div class="column col-8 col-mx-auto col-sm-12">
      <#list [0, 1, 2, 3, 5, 8, 13, 21] as sp>
        <button class="btn btn-action s-circle mr-2 my-2"
                onclick="voteEvent('${roomInfo.room.roomNo}', '${roomInfo.self.id}', ${sp})">${sp}
        </button>
      </#list>
  </div>
  <div class="column col-2 col-mr-auto col-sm-12 mr-2 my-2">
    <button class="btn btn-block"
            onclick="voteEvent(${roomInfo.room.roomNo}, ${roomInfo.self.id}, -1);">
      Cancel
    </button>
  </div>
</div>