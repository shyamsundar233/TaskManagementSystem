import { Outlet } from 'react-router-dom';
import './App.css';
import Title from './Components/Title/Title';
import CustomAlert from './Components/CustomAlert/CustomAlert';
import SideBar from "./Components/SideBar/SideBar";
import TitleBar from "./Components/TitleBar/TitleBar";
import NavBar from "./Components/NavBar/NavBar";

function App() {
  return (
      <div className="app-outer-div">
          <TitleBar/>
          <NavBar/>
          <CustomAlert>
              <div className="outlet-div scroll-div">
                  <Outlet/>
              </div>
          </CustomAlert>
      </div>
  );
}

export default App;
