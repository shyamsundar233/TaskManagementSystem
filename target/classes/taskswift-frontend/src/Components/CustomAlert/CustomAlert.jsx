import { Alert } from '@mui/material';
import React, { createContext, useContext, useState } from 'react'

const AlertContext = createContext();

export const useAlert = () => {
  return useContext(AlertContext);
}

const alertSx = {
  "width": "300px",
  "position" : "absolute",
  "right" : "15px",
  "top" : "15px"
}

const CustomAlert = ({children}) => {

  const [isOpen, setIsOpen] = useState(false);
  const [message, setMessage] = useState('');
  const [severity, setSeverity] = useState('success');

  const showAlert = (tempMessage, tempSeverity) => {
    setIsOpen(true);
    setMessage(tempMessage);
    setSeverity(tempSeverity);
    setTimeout(() => {
      setIsOpen(false);
      setMessage('');
      setSeverity('success');
    },3000)
  }

  return (
    <AlertContext.Provider value={{showAlert}}>
      {isOpen && <Alert sx={alertSx} severity={severity}>{message}</Alert>}
      {children}
    </AlertContext.Provider>
  )
}

export default CustomAlert