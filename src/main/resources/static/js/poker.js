// https://ionicabizau.github.io/emoji.css/#
// https://picturepan2.github.io/spectre/utilities/colors.html
const ACTIONS = {
  FLOP: "FLOP",
  SHUFFLE: "SHUFFLE",
  VOTE: "VOTE",
  PASS: "PASS",
  ONLINE: "ONLINE",
  OFFLINE: "OFFLINE",
};

const sendCmd = async (dataObject) => {
  console.log("send command dataObject=", dataObject);
  try {
    const resp = await fetch(`/api/pokers/${dataObject.pokerId}/cmd`, {
      method: "POST",
      body: JSON.stringify(dataObject),
      credentials: "same-origin",
      headers: {
        "content-type": "application/json;",
      },
    });
    return await resp.json();
  } catch (e) {
    console.error("request error", e);
  }
};

const flopEvent = (roomNo, pokerId) => {
  const action = ACTIONS.FLOP;
  sendCmd({roomNo, pokerId, action}).then((data) => {
    console.log("Send Flop cmd success", data);
  });
};

const shuffleEvent = (roomNo, pokerId) => {
  const action = ACTIONS.SHUFFLE;
  sendCmd({roomNo, pokerId, action}).then((data) => {
    console.log("Send Shuffle cmd success", data);
  });
};

const afterFlop = (pokerId, vote) => {
  const className = ".poker_" + pokerId;
  $(className).find(".card").addClass("bg-success");
  $(className).find(".card-body").html(vote);
};
const afterShuffle = () => {
  const $card = $('.card');
  $card.removeClass("bg-primary");
  $card.removeClass("bg-success");
  $(".card-body").html('<span class="ec ec-zzz"></span>');
};
const afterVoted = (pokerId, isVoted) => {
  const ec = isVoted ? 'ec-100' : 'ec-zzz';
  $(`.poker_${pokerId}`)
    .find(".card-body")
    .html(`<span class="ec ${ec}"></span>`);
};

const voteEvent = (roomNo, pokerId, vote) => {
  const url = `/api/pokers/${pokerId}/vote`;
  let formData = new FormData();
  formData.append("roomNo", roomNo);
  if (vote >= 0) {
    formData.append("vote", vote);
  }
  fetch(url, {
    method: "POST",
    body: formData,
  }).then((resp) => {
    if (resp.ok) {
      resp.text().then((data) => {
        console.log("vote return: ", data);
      });
      afterVoted(pokerId, vote >= 0);
    }
  });
};

const flopListener = (e) => {
  const messages = JSON.parse(e.data);
  let map = new Map();
  messages.map((msg, idx) => {
    afterFlop(msg.pokerId, msg.votes);

    if (map.has(msg.votes)) {
      map.set(msg.votes, map.get(msg.votes) + 1);
    } else {
      map.set(msg.votes, 1);
    }
  });
  let statics = "";
  let votes = 0;
  let p = 0;
  for (let [k, v] of map) {
    const t = `Vote ${k} have ${v}; `;
    statics += t;
    votes += k * v;
    p += v;
  }
  const avg = Math.ceil(votes / p);
  statics += ` Avg: ${avg}`;
  console.log("map .... ", map);
  $(".poker_votes .column").html(statics);
  $(".poker_votes").removeClass("d-hide");
  console.log("flop listener...", messages);
};

const shuffleListener = (e) => {
  console.log("shuffle listener...", e.data);
  afterShuffle();
  $(".poker_votes").addClass("d-hide");
};

const voteListener = (e) => {
  console.log("vote listener...", e.data);
  const message = JSON.parse(e.data);
  afterVoted(message.pokerId, true);
};

const passListener = (e) => {
  console.log("pass listener...", e.data);
  const message = JSON.parse(e.data);
  afterVoted(message.pokerId, false);
};

const onlineListener = (e) => {
  const poker = JSON.parse(e.data);
  console.log(e.data, " online !!!");
  const pokerClass = `poker_${poker.id}`;
  if ($(`.${pokerClass}`).length === 0) {
    const pokerHtml = `
      <div class="column col-2 col-sm-6 col-md-4 col-lg-3 ${pokerClass}">
        <div class="card m-1">
          <div class="card-header text-center p-1">
            <div class="card-title">${poker.name}</div>
          </div>
          <div class="card-body text-center">
            <span class="ec ec-zzz"></span>
          </div>
        </div>
      </div>`;
    $("#pokers").append($(pokerHtml));
  }
};

const offlineListener = (e) => {
  console.log("off line", e.data);
  const message = JSON.parse(e.data);
  $(`.poker_${message.pokerId}`).remove();
};

let evtSource, _roomNo, _pokerId;

const eventSource = (roomNo, pokerId, url) => {
  if (typeof EventSource !== "undefined") {
    _roomNo = roomNo;
    _pokerId = pokerId;
    evtSource = new EventSource(url, {withCredentials: true});

    // 第一次服务器发送消息到客户端时触发。因此不能在该回调方法中发送上线信息。
    evtSource.addEventListener(
      "open",
      function (e) {
        console.log("connected .." + e.currentTarget.url, e.comment);
      },
      false
    );

    evtSource.onerror = function (event) {
      console.log(new Date(), "event source error", event);
      // 0:connecting 1:open 2:closed
      if (this.readyState === 0) {
        console.log("connecting retry...");
      } else if (this.readyState === 2) {
        console.log("close .....");
        //this.close();
      }
    };

    evtSource.addEventListener(ACTIONS.FLOP, flopListener);
    evtSource.addEventListener(ACTIONS.SHUFFLE, shuffleListener);
    evtSource.addEventListener(ACTIONS.VOTE, voteListener);
    evtSource.addEventListener(ACTIONS.PASS, passListener);
    evtSource.addEventListener(ACTIONS.ONLINE, onlineListener);
    evtSource.addEventListener(ACTIONS.OFFLINE, offlineListener);

    return evtSource;
  } else {
    console.error("Sorry! No server-sent events support.");
  }
};

const handleOfflineEvent = (roomNo, pokerId) => {
  const url = `/api/subscriber/${pokerId}/unsubscribe/${roomNo}`;
  fetch(url, {
    method: "POST",
  }).then((resp) => {
    console.log("reps:", resp);
    evtSource.close();
  });
};

window.addEventListener("beforeunload", (event) => {
  //evtSource.close();
  //event.preventDefault();
  //handleOfflineEvent(_roomNo, _pokerId);
});
