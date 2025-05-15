import { createContext, useContext } from "react";

export const ColourModeContext = createContext({
  mode: "light",
  toggle: () => {},
});

export function useColourMode() {
  return useContext(ColourModeContext);
}
