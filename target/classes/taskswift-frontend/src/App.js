import { Outlet } from 'react-router-dom';
import './App.css';
import Title from './Components/Title/Title';
import CustomAlert from './Components/CustomAlert/CustomAlert';
import SideBar from "./Components/SideBar/SideBar";
import {useEffect} from "react";
import StockJS from "sockjs-client";
import Stomp from "stompjs";

function App() {

    useEffect(() => {
        let stompClient = null;
        let privateStompClient = null;

        let socket = new StockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function(frame) {
            console.log(frame);
            stompClient.subscribe('/all/messages', function(result) {
                console.log(JSON.parse(result.body));
                //show(JSON.parse(result.body));
            });
            //stompClient.send('/app/application', {}, JSON.stringify({text: "test message" }));
        });
        socket = new StockJS('/ws');
        privateStompClient = Stomp.over(socket);
        privateStompClient.connect({}, function(frame) {
            console.log(frame);
            privateStompClient.subscribe('/user/specific', function(result) {
                console.log(JSON.parse(result.body));
                //show(JSON.parse(result.body));
            });
            stompClient.send('/app/private', {}, JSON.stringify({'text':'test message 1', 'to':'Shyam233_10'}));
        });

    },[])

  return (
      <div className="app-outer-div">
          <Title/>
          <SideBar/>
          <CustomAlert>
              <div className="outlet-div scroll-div">
                  <Outlet/>
              </div>
          </CustomAlert>
      </div>
  );
}

export default App;
