/**
 * Created by baoya on 18/2/6.
 */


function sendMsg(message ) {//发送消息
    //var msgStr  =   JSON.stringify(message);
    websocket.send(message);

}


function connectYunliaoServer(){
    var url = "ws://localhost:9999";
    websocket = new WebSocket(url);
    websocket.onopen = function(){

    }// 连接打开
    websocket.onmessage = function(event){
        //alert(event.data);
        console.log(event.data)
        if (event.data == "HEARTBEAT"){// 心跳检测
            websocket.send("ACK_HEARTBEAT");
        }else{
            var message = JSON.parse(event.data);
            var msg= message.msg;
            $("#msgPanel").html( $("#msgPanel").html() +"\r\n"+ event.data);
        }
    }
    websocket.onclose = function(){
        setTimeout(function(){
            connectYunliaoServer();
        }, 3000);
    }
    websocket.onerror =  function(event){
        console.error(event);
    }
    return websocket;
}
