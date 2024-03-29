import "./TableComponent.css";
import TableContainer from "@mui/material/TableContainer";
import Paper from "@mui/material/Paper";
import Table from "@mui/material/Table";
import TableHead from "@mui/material/TableHead";
import TableBody from "@mui/material/TableBody";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";

const TableComponent = ({headerRow, bodyRow, classList}) => {
    classList = classList + " table-comp-cont-1 height-width-100";
  return (
      <TableContainer component={Paper} className={classList}>
          <Table stickyHeader sx={{ minWidth: 650 }} aria-label="simple table">
              <TableHead>
                  <TableRow>
                      {headerRow.map(row => {
                          return (
                              <TableCell className="font-bold">{row}</TableCell>
                          );
                      })}
                  </TableRow>
              </TableHead>
              <TableBody className="scroll-div">
                  {bodyRow.map(row => {
                      return (
                          <TableRow>
                              {row.map(col => {
                                  return (
                                      <TableCell>{col}</TableCell>
                                  );
                              })}
                          </TableRow>
                      )
                  })}
              </TableBody>
          </Table>
      </TableContainer>
  );
}

export default TableComponent;