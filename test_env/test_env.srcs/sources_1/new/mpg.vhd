----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 03/02/2017 01:15:08 PM
-- Design Name: 
-- Module Name: mpg - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- 
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity mpg is
    Port ( btn : in STD_LOGIC_VECTOR (4 downto 0);
           clk : in STD_LOGIC;
           step : out STD_LOGIC_VECTOR (4 downto 0));
end mpg;

architecture Behavioral of mpg is

signal cnt : std_logic_vector( 15 downto 0);
signal d1: std_logic_vector (4 downto 0);
signal d2: std_logic_vector (4 downto 0);                                          
signal d3: std_logic_vector (4 downto 0);
signal en: std_logic;

begin

    process (clk)
    begin
    if rising_edge(clk) then
          cnt <= cnt + 1;
       end if;
    end process;
    
    process(cnt)
    begin
        if cnt="1111111111111111" then
            en<='1';
        else
            en<='0';
        end if;
    end process;
    
    process(clk,en)
    begin
    if rising_edge(clk) then
        if en='1' then
            d1<=btn;
            end if;
            end if;
    end process;
    
    process(clk)
        begin
        if rising_edge(clk) then
                d2<=d1;
                end if;
        end process;
        
    process(clk)
    begin
        if rising_edge(clk) then
              d3<=d2;
              end if;
        end process;  
   
   step<=d2 and (not d3);  
    


end Behavioral;
