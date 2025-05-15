import React, { useMemo, useState, useCallback, useEffect } from 'react';
import { lightTheme, darkTheme } from './theme';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import { ColourModeContext } from './ColourModeContext';



export function ColorModeProvider({ children }) {

    const [mode, setMode] = useState(
        () => localStorage.getItem('mode') || 'light');


    const toggleTheme = useCallback(() => {
        setMode(prev => {
            const next = prev === 'light' ? 'dark' : 'light';
            localStorage.setItem('mode', next);
            return next;
        });
    }, []);

    // Set custom theme when mode changes
    const theme = useMemo(() =>
        createTheme(mode === 'dark' ? darkTheme : lightTheme), [mode]);

    const value = { mode, toggleTheme };

    return (
        <ColourModeContext.Provider value={value}>
            <ThemeProvider theme={theme}>
                {children}
            </ThemeProvider>
        </ColourModeContext.Provider>

    );

}