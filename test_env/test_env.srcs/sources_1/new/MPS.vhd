----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 03/02/2017 01:14:04 PM
-- Design Name: 
-- Module Name: MPS - Behavioral
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

entity MPS is
    Port ( clk : in STD_LOGIC;
           btn : in STD_LOGIC_VECTOR (4 downto 0);
           step : out STD_LOGIC_VECTOR (4 downto 0));
end MPS;

architecture Behavioral of MPS is

    signal Q0:std_logic_vector(4 downto 0);
    signal Q1:std_logic_vector(4 downto 0);
    signal Q2:std_logic_vector(4 downto 0);
    signal cnt:std_logic_vector(17 downto 0);
    signal en:std_logic;

begin

    process(clk,cnt)
    begin
        if clk'event and clk = '1' then          
            cnt <= cnt + 1;
        end if;
        if cnt = x"FFFFFF" then
            en <= '1';
        end if;
    end process;

    process(clk,cnt)
    begin
        if clk'event and clk = '1' then
          if en = '1' then
             Q0 <= btn;
          else
              
          end if;
        end if;
    end process;
    
    process(clk)
    begin 
        if clk'event and clk = '1' then
            Q1 <= Q0;
        end if;
    end process;
    
    process(clk)
        begin 
            if clk'event and clk = '1' then
                Q2 <= Q1;
            end if;
     end process;
     
     step <= Q1 and (not Q2);
        
end Behavioral;
