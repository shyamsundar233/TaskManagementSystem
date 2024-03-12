import { Outlet } from 'react-router-dom';
import './App.css';
import Title from './Components/Title/Title';
import CustomAlert from './Components/CustomAlert/CustomAlert';
import SideBar from "./Components/SideBar/SideBar";

function App() {

  return (
      <div className="app-outer-div">
          <Title/>
          <SideBar/>
          <CustomAlert>
              <div className="outlet-div">
                  <Outlet/>
              </div>
          </CustomAlert>
      </div>
  );
}

export default App;
