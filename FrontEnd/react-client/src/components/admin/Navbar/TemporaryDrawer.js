import React from 'react';
import MuiAppBar from "@mui/material/AppBar";
import { styled, useTheme } from "@mui/material/styles";
import { useState } from 'react';
import MuiDrawer from "@mui/material/Drawer";
import Box from '@mui/material/Box';
import CssBaseline from "@mui/material/CssBaseline";
import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import MenuBookIcon from '@mui/icons-material/MenuBook';
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import Tooltip from "@mui/material/Tooltip";
import Avatar from '@mui/material/Avatar';
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import { Link } from "react-router-dom";
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import { Divider, List, ListItem, ListItemButton, ListItemIcon, ListItemText } from '@mui/material';
import AddBoxIcon from "@mui/icons-material/AddBox";
import CheckBoxRoundedIcon from "@mui/icons-material/CheckBoxRounded";
import PersonOutlineRoundedIcon from "@mui/icons-material/PersonOutlineRounded";
import BookRoundedIcon from '@mui/icons-material/BookRounded';
import PersonPinRoundedIcon from '@mui/icons-material/PersonPinRounded';


const drawerWidth = 240;

const pages = ["Add Book","Add Author","Unblock User"];
const settings = ["Logout"];

const openedMixin = (theme) => ({
  width: drawerWidth,
  transition: theme.transitions.create("width", {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.enteringScreen,
  }),
  overflowX: "hidden",
});

const closedMixin = (theme) => ({
  transition: theme.transitions.create("width", {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),
  overflowX: "hidden",
  width: `calc(${theme.spacing(7)} + 1px)`,
  [theme.breakpoints.up("sm")]: {
    width: `calc(${theme.spacing(8)} + 1px)`,
  },
});

const DrawerHeader = styled("div")(({ theme }) => ({
  display: "flex",
  alignItems: "center",
  justifyContent: "flex-end",
  padding: theme.spacing(0, 1),
  // necessary for content to be below app bar
  ...theme.mixins.toolbar,
}));

const AppBar = styled(MuiAppBar, {
  shouldForwardProp: (prop) => prop !== "open",
})(({ theme, open }) => ({
  zIndex: theme.zIndex.drawer + 1,
  transition: theme.transitions.create(["width", "margin"], {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),
  ...(open && {
    marginLeft: drawerWidth,
    width: `calc(100% - ${drawerWidth}px)`,
    transition: theme.transitions.create(["width", "margin"], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  }),
}));

const Drawer = styled(MuiDrawer, {
  shouldForwardProp: (prop) => prop !== "open",
})(({ theme, open }) => ({
  width: drawerWidth,
  flexShrink: 0,
  whiteSpace: "nowrap",
  boxSizing: "border-box",
  ...(open && {
    ...openedMixin(theme),
    "& .MuiDrawer-paper": openedMixin(theme),
  }),
  ...(!open && {
    ...closedMixin(theme),
    "& .MuiDrawer-paper": closedMixin(theme),
  }),
}));

export default function TemporaryDrawer() {
  const theme = useTheme();
  const [open, setOpen] = React.useState(false);
  const [anchorElNav, setAnchorElNav] = React.useState(null);
  const [anchorElUser, setAnchorElUser] = React.useState(null);
  

  const handleOpenNavMenu = (event) => {
    setAnchorElNav(event.currentTarget);
  };
  const handleOpenUserMenu = (event) => {
    localStorage.clear();
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseNavMenu = (event) => {
    setAnchorElNav();
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const handleDrawerClose = () => {
    setOpen(false);
  };

  return (
    <Box sx={{ display: "flex" }}>
      <CssBaseline />
      <AppBar position="fixed" open={open}>
      <Toolbar>
      <IconButton
            color="inherit"
            aria-label="open drawer"
            onClick={handleDrawerOpen}
            edge="start"
            sx={{
              marginRight: 5,
              ...(open && { display: "none" }),
            }}
          >
            <MenuIcon />
          </IconButton>
          <MenuBookIcon sx={{ display: { xs: "none", md: "flex" }, mr: 1 }} />
          <Typography
            variant="h6"
            noWrap
            component="a"
            href="/"
            sx={{
              mr: 2,
              display: { xs: "none", md: "flex" },
              fontFamily: "monospace",
              fontWeight: 700,
              letterSpacing: ".3rem",
              color: "inherit",
              textDecoration: "none",
            }}
          >
            Book Shop
          </Typography>
          <Box sx={{ flexGrow: 1, display: { xs: "none", md: "flex" } }}>
          <Button
              onClick={handleCloseNavMenu}
              sx={{ my: 2, color: "white", display: "block" }}
              href="/"
            >
              {pages[0]}
            </Button>
            <Button
              onClick={handleCloseNavMenu}
              sx={{ my: 2, color: "white", display: "block" }}
              href="/"
            >
              {pages[1]}
            </Button>
            <Button
              onClick={handleCloseNavMenu}
              sx={{ my: 2, color: "white", display: "block" }}
              href="/"
            >
              {pages[2]}
            </Button>
          </Box>
          <Box sx={{ flexGrow: 0 }}>
          <Tooltip title="Open settings">
              <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                <Avatar id="avatar" alt="Remy Sharp" src="/static/images/avatar/2.jpg" />
                &nbsp;<Typography id="avatar" component="legend">Welcome Admin</Typography>
              </IconButton>
            </Tooltip>
            <Menu
              sx={{ mt: "45px" }}
              id="menu-appbar"
              anchorEl={anchorElUser}
              anchorOrigin={{
                vertical: "top",
                horizontal: "right",
              }}
              keepMounted
              transformOrigin={{
                vertical: "top",
                horizontal: "right",
              }}
              open={Boolean(anchorElUser)}
              onClose={handleCloseUserMenu}
            >
              {settings.map((setting) => (
                <MenuItem key={setting} onClick={handleCloseUserMenu}>
                  {/* <Link to="/" id='logout'> */}
                    <Typography textAlign="center">{setting}</Typography>
                  {/* </Link> */}
                </MenuItem>
              ))}  
            </Menu>
          </Box>
      </Toolbar>
      </AppBar>
      <Drawer variant="permanent" open={open}>
      <DrawerHeader>
          <IconButton onClick={handleDrawerClose}>
          {theme.direction === "rtl" ? (
              <ChevronRightIcon />
            ) : (
              <ChevronLeftIcon />
            )}
          </IconButton>
          </DrawerHeader>
          <Divider />
          <List>
          {["Add Book", "Unblock User", "Add Author"].map((text, index) => (
            <ListItem key={text} disablePadding sx={{ display: "block" }}>
              <ListItemButton
                sx={{
                  minHeight: 48,
                  justifyContent: open ? "initial" : "center",
                  px: 2.5,
                }}
              >
                <ListItemIcon
                  sx={{
                    minWidth: 0,
                    mr: open ? 3 : "auto",
                    justifyContent: "center",
                  }}
                >
                  {/* <Link to="/"> */}
                    {index === 0 && <AddBoxIcon />}
                  {/* </Link> */}
                  {/* <Link to="/"> */}
                    {index === 1 && <CheckBoxRoundedIcon />}
                  {/* </Link> */}
                  {/* <Link to="/"> */}
                    {index === 2 && <AddBoxIcon />}
                  {/* </Link> */}
                </ListItemIcon>
                <ListItemText primary={text} sx={{ opacity: open ? 1 : 0 }} />
              </ListItemButton>
            </ListItem>
          ))}
          </List>
          <Divider />
        <List>
          {[
            "All Users",
            "All Books",
            "All Authors",
          ].map((text, index) => (
            <ListItem key={text} disablePadding sx={{ display: "block" }}>
              <ListItemButton
                sx={{
                  minHeight: 48,
                  justifyContent: open ? "initial" : "center",
                  px: 2.5,
                }}
              >
                <ListItemIcon
                  sx={{
                    minWidth: 0,
                    mr: open ? 3 : "auto",
                    justifyContent: "center",
                  }}
                >
                  {/* <Link to="/"> */}
                    {index === 0 && <PersonOutlineRoundedIcon />}
                    {/* </Link> */}
                  <Link to="/getAllBooks">
                    {index === 1 && <BookRoundedIcon />}
                    </Link>
                  {/* <Link to="/"> */}
                    {index === 2 && <PersonPinRoundedIcon />}
                    {/* </Link> */}
                </ListItemIcon>
                <ListItemText primary={text} sx={{ opacity: open ? 1 : 0 }} />
              </ListItemButton>
            </ListItem>
          ))}
        </List>
      </Drawer>
      <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
        <DrawerHeader />
      </Box>
    </Box>

  )


}