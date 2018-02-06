/**
 * Created by baoya on 18/2/6.
 */

function register() {//注册
    var value = $("#testRegister").val();

    var bytes = new Array();
    bytes.push(0x66);//写入魔数
    bytes.push(0x01);//消息标志，请求
    bytes.push(0x02);//消息类型,发送个人消息
    bytes.push(0x01);//请求成功

    var msgStr  = value;
    var bytesMsg  =  stringToByte(msgStr);
    var bytesMsgLen = getInt64Bytes(bytesMsg.length);

    for(var i in bytesMsgLen){
        bytes.push(bytesMsgLen[i]);
    }
    for(var i in bytesMsg){
        bytes.push(bytesMsg[i]);
    }
    websocket.send(byteToString(bytes));
}
function sendMsg() {//发送消息
    var value = $("#testMsg").val();

    var bytes = new Array();
    bytes.push(0x66);//写入魔数
    bytes.push(0x01);//消息标志，请求
    bytes.push(0x10);//消息类型,发送个人消息
    bytes.push(0x01);//请求成功



    var chatMessage = {
        "fromId":$("#testRegister").val(),
        "toId":$("#toUser").val(),
        "msg":value
    };
    var msgStr  =   JSON.stringify(chatMessage);
    var bytesMsg  =  stringToByte(msgStr);
    var bytesMsgLen = getInt64Bytes(bytesMsg.length);

    for(var i in bytesMsgLen){
        bytes.push(bytesMsgLen[i]);
    }
    for(var i in bytesMsg){
        bytes.push(bytesMsg[i]);
    }

    websocket.send(byteToString(bytes));
    //websocket.send(value);
}


function connectYunliaoServer(){
    var url = "ws://localhost:8999";
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
            var fromId = message.fromId;
            var msg= message.msg;
            var content = fromId+"："+msg;
            //alert(content);
            $("#msgPanel").html( $("#msgPanel").html() +"\r\n"+ content);
        }
    }
    websocket.onclose = function(){
        setTimeout(function(){
            connectYunliaoServer();
        }, 3000);
    }
    websocket.onerror =  function(event){
        console.error(event);
        //alert(event.data);
    }
    return websocket;
}

function stringToByte(str) {
    var bytes = new Array();
    var len, c;
    len = str.length;
    for(var j=0; j< len;j++){
        c = str.charCodeAt(j);
        if(c >= 0x010000 && c <= 0x10FFFF) {
            bytes.push(((c >> 18) & 0x07) | 0xF0);
            bytes.push(((c >> 12) & 0x3F) | 0x80);
            bytes.push(((c >> 6) & 0x3F) | 0x80);
            bytes.push((c & 0x3F) | 0x80);
        } else if(c >= 0x000800 && c <= 0x00FFFF) {
            bytes.push(((c >> 12) & 0x0F) | 0xE0);
            bytes.push(((c >> 6) & 0x3F) | 0x80);
            bytes.push((c & 0x3F) | 0x80);
        } else if(c >= 0x000080 && c <= 0x0007FF) {
            bytes.push(((c >> 6) & 0x1F) | 0xC0);
            bytes.push((c & 0x3F) | 0x80);
        } else {
            bytes.push(c & 0xFF);
        }
    }
    return bytes;
}

function byteToString(arr) {
    if(typeof arr === 'string') {
        return arr;
    }
    var str = '',
        _arr = arr;
    for(var i = 0; i < _arr.length; i++) {
        var one = _arr[i].toString(2),
            v = one.match(/^1+?(?=0)/);
        if(v && one.length == 8) {
            var bytesLength = v[0].length;
            var store = _arr[i].toString(2).slice(7 - bytesLength);
            for(var st = 1; st < bytesLength; st++) {
                store += _arr[st + i].toString(2).slice(2);
            }
            str += String.fromCharCode(parseInt(store, 2));
            i += bytesLength - 1;
        } else {
            str += String.fromCharCode(_arr[i]);
        }
    }
    return str;
}

function getInt64Bytes( x ){
    //var bytes = [0x00,0x00,0x00,0x00];
    var bytes =[];
    var i = 4;
    do {
        bytes[--i] = x & (255);
        x = x>>8;
    } while ( i )
    return bytes;
}