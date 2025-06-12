import { useState, useLayoutEffect } from 'react';
import { styled, useTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import MuiDrawer from '@mui/material/Drawer';
import MuiAppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import CssBaseline from '@mui/material/CssBaseline';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import InboxIcon from '@mui/icons-material/MoveToInbox';
import MailIcon from '@mui/icons-material/Mail';
import { Outlet, useNavigate } from 'react-router';
import DarkModeToggle from '../Theme/DarkModeToggle';
import useAuth from '../hooks/useAuth';
import { ROLES } from '../Util/constants';
import useLogout from '../hooks/useLogout';

import Stack from '@mui/material/Stack';
import LogoutButton from '../components/LogoutButton';



const drawerWidth = 240;

const openedMixin = (theme) => ({
    width: drawerWidth,
    transition: theme.transitions.create('width', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.enteringScreen,
    }),
    overflowX: 'hidden',
});

const closedMixin = (theme) => ({
    transition: theme.transitions.create('width', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
    }),
    overflowX: 'hidden',
    width: `calc(${theme.spacing(7)} + 1px)`,
    [theme.breakpoints.up('sm')]: {
        width: `calc(${theme.spacing(8)} + 1px)`,
    },
});

const DrawerHeader = styled('div')(({ theme }) => ({
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
}));


const AppBar = styled(MuiAppBar, {
    shouldForwardProp: (prop) => prop !== 'open',
})(({ theme }) => ({
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(['width', 'margin'], {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
    }),
    variants: [
        {
            props: ({ open }) => open,
            style: {
                marginLeft: drawerWidth,
                width: `calc(100% - ${drawerWidth}px)`,
                transition: theme.transitions.create(['width', 'margin'], {
                    easing: theme.transitions.easing.sharp,
                    duration: theme.transitions.duration.enteringScreen,
                }),
            },
        },
    ],
}));

const Drawer = styled(MuiDrawer, { shouldForwardProp: (prop) => prop !== 'open' })(
    ({ theme }) => ({
        width: drawerWidth,
        flexShrink: 0,
        whiteSpace: 'nowrap',
        boxSizing: 'border-box',
        variants: [
            {
                props: ({ open }) => open,
                style: {
                    ...openedMixin(theme),
                    '& .MuiDrawer-paper': openedMixin(theme),
                },
            },
            {
                props: ({ open }) => !open,
                style: {
                    ...closedMixin(theme),
                    '& .MuiDrawer-paper': closedMixin(theme),
                },
            },
        ],
    }),
);




export function Shell() {
    const theme = useTheme();
    const [open, setOpen] = useState(false);
    const navigate = useNavigate();
    const { hasRole } = useAuth();
    const logout = useLogout();

    const handleDrawerOpen = () => {
        setOpen(true);
    };

    const handleDrawerClose = () => {
        setOpen(false);
    };


    return (
        <Box sx={{ display: 'flex' }}>
            <CssBaseline />
            <AppBar position="fixed" open={open}>
                <Toolbar>
                    <IconButton
                        color="inherit"
                        aria-label="open drawer"
                        onClick={handleDrawerOpen}
                        edge="start"
                        sx={[
                            {
                                marginRight: 5,
                            },
                            open && { display: 'none' },
                        ]}
                    >
                        <MenuIcon />
                    </IconButton>
                    <Typography variant="h6" noWrap component="div">
                        User Management Service
                    </Typography>
                </Toolbar>

            </AppBar>

            <Drawer variant="permanent" open={open}>
                <DrawerHeader>
                    <IconButton onClick={handleDrawerClose}>
                        {theme.direction === 'rtl' ? <ChevronRightIcon /> : <ChevronLeftIcon />}
                    </IconButton>
                </DrawerHeader>
                <Divider />
                <Box sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    height: '100vh'
                }}>

                    <List>
                        {['Home', 'Applications'].map((text, index) => (
                            <ListItem key={text} disablePadding sx={{ display: 'block' }}>
                                <ListItemButton
                                    sx={[
                                        {
                                            minHeight: 48,
                                            px: 2.5,
                                        },
                                        open
                                            ? {
                                                justifyContent: 'initial',
                                            }
                                            : {
                                                justifyContent: 'center',
                                            },

                                    ]}
                                    onClick={() => {
                                        if (text === "Home") {
                                            navigate("/")
                                        } else {
                                            navigate(`/${text.toLowerCase()}`)

                                        }


                                    }
                                    }
                                >
                                    <ListItemIcon
                                        sx={[
                                            {
                                                minWidth: 0,
                                                justifyContent: 'center',
                                            },
                                            open
                                                ? {
                                                    mr: 3,
                                                }
                                                : {
                                                    mr: 'auto',
                                                },
                                        ]}
                                    >
                                        {index % 2 === 0 ? <InboxIcon /> : <MailIcon />}
                                    </ListItemIcon>
                                    <ListItemText
                                        primary={text}
                                        sx={[
                                            open
                                                ? {
                                                    opacity: 1,
                                                }
                                                : {
                                                    opacity: 0,
                                                },
                                        ]}
                                    />
                                </ListItemButton>
                            </ListItem>
                        ))}
                    </List>
                    <Divider />


                    {hasRole(ROLES[1]) && (
                        <List>
                            {['Maintain Users'].map((text, index) => (
                                <ListItem key={text} disablePadding sx={{ display: 'block' }}>
                                    <ListItemButton
                                        sx={[
                                            {
                                                minHeight: 48,
                                                px: 2.5,
                                            },
                                            open
                                                ? {
                                                    justifyContent: 'initial',
                                                }
                                                : {
                                                    justifyContent: 'center',
                                                },
                                        ]}


                                        onClick={() => {
                                            navigate(`/admin/${text.replace(" ", "")}`)
                                        }}
                                    >
                                        <ListItemIcon
                                            sx={[
                                                {
                                                    minWidth: 0,
                                                    justifyContent: 'center',
                                                },
                                                open
                                                    ? {
                                                        mr: 3,
                                                    }
                                                    : {
                                                        mr: 'auto',
                                                    },
                                            ]}
                                        >
                                            {index % 2 === 0 ? <InboxIcon /> : <MailIcon />}
                                        </ListItemIcon>
                                        <ListItemText
                                            primary={text}
                                            sx={[
                                                open
                                                    ? {
                                                        opacity: 1,
                                                    }
                                                    : {
                                                        opacity: 0,
                                                    },
                                            ]}
                                        />
                                    </ListItemButton>
                                </ListItem>
                            ))}
                        </List>
                    )}
                </Box>

                <Box sx={{
                    marginBottom: '10%',
                    alignItems: 'center',
                    justifyContent: 'center'

                }}>

                    <Stack direction={open ? "row" : "column"} spacing={1} alignItems="center" justifyContent="center" divider={<Divider flexItem />}>
                        <LogoutButton></LogoutButton>
                        <DarkModeToggle></DarkModeToggle>
                    </Stack>

                </Box>


            </Drawer>

            <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
                <DrawerHeader />
                <Outlet />
            </Box>
        </Box>
    );
}

export default Shell;