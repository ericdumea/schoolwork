----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 03/02/2017 12:12:45 PM
-- Design Name: 
-- Module Name: test_env - Behavioral
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

entity test_env is
    Port ( clk : in STD_LOGIC;
           btn : in STD_LOGIC_VECTOR (4 downto 0);
           sw : in STD_LOGIC_VECTOR (15 downto 0);
           led : out STD_LOGIC_VECTOR (15 downto 0);
           an : out STD_LOGIC_VECTOR (3 downto 0);
           cat : out STD_LOGIC_VECTOR (6 downto 0);
           txd: out std_logic;
           rxd : in std_logic);
end test_env;

architecture Behavioral of test_env is

    signal cnt: std_logic_vector(15 downto 0):=(others => '0');
    signal dbtn:std_logic_vector(4 downto 0);
    signal rez : std_logic_vector(15 downto 0);
    signal rom_data: std_logic_vector(15 downto 0);
    
component MPS
Port ( clk : in STD_LOGIC;
           btn : in STD_LOGIC_VECTOR (4 downto 0);
           step : out STD_LOGIC_VECTOR (4 downto 0));
end component;

component seven_seg is
    Port ( inp : in STD_LOGIC_VECTOR (15 downto 0);
           clk : in STD_LOGIC;
           an : out STD_LOGIC_VECTOR (3 downto 0);
           cat : out STD_LOGIC_VECTOR (6 downto 0));
end component;

   -- signal a: std_logic_vector(15 downto 0);
   -- signal b: std_logic_vector(15 downto 0);
   -- signal c: std_logic_vector(15 downto 0);
    type vector is array(0 to 255) of std_logic_vector(15 downto 0);
    signal rom: vector :=(
    -- op   rS  rT  rd sa fct
     -- B"000_011_010_001_0_000",--add RF[3]<-RF[1]+RF[2]                 0
    --  B"000_001_000_011_0_000",--add RF[3]<-RF[1]+RF[2] -- rd<-rs+rt    1
     -- B"000_011_010_000_0_000",--add RF[0]<-RF[3]+RF[2]                 2
     -- B"000_100_010_111_0_001",--sub RF[7]<-RF[4]-RF[2]                 3
     -- B"000_000_010_111_1_010",--sll RF[8]<-RF[2] << sa                 4
     -- B"000_000_010_111_1_011",--srl RF[8]<-RF[2] >> sa                 5
     -- B"000_010_001_111_0_100",--and RF[8]<-RF[2] and RF[1]             6
     -- B"000_010_001_111_0_101",--or  RF[8]<-RF[2] or RF[1]              7       
     -- B"000_001_010_111_0_110",--nand  RF[8]<-RF[1] nand RF[2]          8
    --  B"000_001_010_111_0_111",--xor  RF[8]<- RF[1] xor RF[2]           9
      
     -- B"001_001_010_0000001",--addi                                     10 
     -- B"010_000_011_0000011",--lw                                       11
     -- B"011_000_011_0000011",--sw                                       12  
     -- B"100_011_010_0000111",--beq                                      13      
     -- B"101_010_011_0000111",--xori                                     14
     -- B"110_011_001_0000011",--ori                                      15    
     -- B"111_0000000000001",--j                                          16
     B"000_000_000_000_0_111",--xor  RF[0]<- RF[0] xor RF[0]
     B"000_001_001_001_0_111",--xor  RF[1]<- RF[1] xor RF[1]
     B"000_010_010_010_0_111",--xor  RF[2]<- RF[2] xor RF[2]
     B"000_011_011_011_0_111",--xor  RF[3]<- RF[3] xor RF[3]
     
     B"001_001_001_0000001",--addi rf[1]=rf[1]+1                          10
     B"001_010_010_0000001",--addi rf[2]=rf[2]+1   
     
     B"000_001_010_011_0_000",--add RF[3]<-RF[1]+RF[2]
      
     B"000_000_010_001_0_000",--add RF[1]<-RF[2]+RF[0]
     B"000_000_011_010_0_000",--add RF[2]<-RF[3]+RF[0]  
     
     B"100_011_100_0000001",--beq when RF[3] = 22
     B"111_0000000000110",--j to instr 6
     
     B"011_000_011_0000011",--sw
     
     B"010_000_001_0000011",--lw
     
     
     B"111_0000000000000",--j to instr 0
     
                        
      others=>x"0000");
      
     type regvec is array(0 to 15) of std_logic_vector(15 downto 0);
     signal reg: regvec:=(
        x"0000",
        x"0002",
        x"0003",
        x"0004",
        x"0179",
        x"0006",
        x"0007",
        others=>x"0000");
        
     signal ram: vector:=(
        x"6454",
        x"FFFF",
        x"ABBB",
        x"BEBB",
        x"EEEE",
        x"BABA",
        x"BEBB",
        x"EEEE",
        x"BABA",
        x"BEBB",
        x"EEEE",
        x"BABA",
        x"BEBB",
        x"EEEE",
        x"BABA",
        others=>x"0000");
        
     signal ra1: std_logic_vector(2 downto 0);
     signal ra2: std_logic_vector(2 downto 0);
     signal wa: std_logic_vector(2 downto 0);
     signal rd1: std_logic_vector(15 downto 0);
     signal rd2: std_logic_vector(15 downto 0);
     signal wd: std_logic_vector(15 downto 0);
     signal ext:std_logic_vector(15 downto 0);
     
     signal ram_data: std_logic_vector(15 downto 0):=(others=>'0');
     signal ram_data_in: std_logic_vector(15 downto 0);
    
     signal branch_addr:std_logic_vector(15 downto 0):=x"0003";
     signal jmp_addr:std_logic_vector(15 downto 0):=x"0000";
     
     signal RegDst,rwrite,jump,alusrc,memread,memwr,memtoreg,branch:std_logic;
     signal extOp:std_logic:='0';
     
      signal aluop : std_logic_vector(2 downto 0):="000";
     
     signal a:std_logic_vector(15 downto 0);
     signal b:std_logic_vector(15 downto 0);
     
     signal aluctrl:std_logic_vector(2 downto 0);
     signal sa:std_logic;
     
     signal alurez:std_logic_vector(15 downto 0);
     signal ram_out:std_logic_vector(15 downto 0);
     signal z:std_logic:='0';
     
     type state_type is(st_idle, st_start, st_bit, st_stop);
     type state_type_rx is(st_idle, st_start, st_bit, st_stop,st_wait);
     
     signal state : state_type:=st_idle;
     signal rst:std_logic;
     signal bit_cnt:std_logic_vector(2 downto 0):="000";
     signal tx_en : std_logic;
     signal tx_data : std_logic_vector(7 downto 0);
     signal tx_data1 : std_logic_vector(7 downto 0);
     signal tx_rdy, tx :std_logic;
     signal baud_en : std_logic:='0';
     signal baud_count : std_logic_vector(15 downto 0):=x"0000";
     signal tf : std_logic_vector(1 downto 0):="00";
     signal tx_cnt: std_logic_vector(1 downto 0) := "00";
     signal baud_cnt :std_logic_vector(3 downto 0) := "0000";
     
     --pipeline signals -- 
     
     signal rom_p1 : std_logic_vector(15 downto 0);
     signal rom_p2 : std_logic_vector(15 downto 0);
     signal rom_p3 : std_logic_vector(15 downto 0);
     signal rom_p4 : std_logic_vector(15 downto 0);
     
     --FSM RX
     
     signal state_rx :state_type_rx:=st_idle;
     signal rx , rx_rdy : std_logic;
     signal rx_data : std_logic_vector(7 downto 0):="00000000";
     signal bit_cnt1:std_logic_vector(2 downto 0):="000";
     signal baud_count1 : std_logic_vector(10 downto 0);
     signal baud_en1 : std_logic := '0';
     
               
begin

P1: MPS port map (clk => clk, btn => btn, step => dbtn);
p2: seven_seg port map(inp=> rez,clk=>clk, an=>an, cat=>cat);

    --PIPELINE--
    
    IFReg : process(clk,dbtn(3))
    begin
        if rising_edge(clk) then
            if dbtn(3) = '1' then
                rom_p1 <= rom_data;
            end if;
        end if;
    end process IFReg;
    
    IDReg: process(clk,dbtn(3))
        begin
            if rising_edge(clk) then
                if dbtn(3) = '1' then
                    rom_p1 <= rom_data;
              end if;
          end if;
      end process IDReg;
    
    --FSM lab12
    
    rx<=rxd;
    
    process(baud_en1,rst,rx,clk)
    begin
        if (dbtn(2) ='1') then
            state_rx <=st_idle;
        elsif rising_edge(clk) then
                    if baud_en1='1' then
                        case state_rx is
                            when st_wait => if(baud_cnt <7) then
                                                state_rx <= st_wait;
                                                baud_cnt <= baud_cnt + '1';
                                            elsif(baud_cnt = 7) then
                                                state_rx <= st_idle;
                                                baud_cnt <= "0000";
                                               
                                            end if;

                            when st_idle => if rx = '0' then
                                                state_rx <= st_start;
                                                baud_cnt <= "0000";
                                            else
                                                state_rx <= st_idle;
                                                baud_cnt <= "0000";
                                            end if;
                                                bit_cnt1 <= "000";
                            when st_start => if rx = '1' then
                                                state_rx <=st_idle;
                                                baud_cnt <= "0000";
                                             elsif baud_cnt = 7 then
                                                state_rx <= st_bit;
                                                baud_cnt <= "0000";
                                             else
                                                state_rx <= st_start;
                                                baud_cnt <= baud_cnt + '1';
                                             end if;
                            when st_bit => if bit_cnt1=7 then
                                            if(baud_cnt = 15) then
                                                rx_data(conv_integer(bit_cnt1)) <=rx;
                                                state_rx <= st_stop;
                                                baud_cnt <= "0000";
                                            else
                                                state_rx <= st_bit;
                                                baud_cnt <=baud_cnt+ '1';
                                            end if;
                                            else
                                                if(baud_cnt = 15) then
                                                rx_data(conv_integer(bit_cnt1)) <=rx;
                                                state_rx <= st_stop;
                                                baud_cnt <= "0000";
                                                bit_cnt1 <= bit_cnt1 + '1';
                                                else
                                                    baud_cnt <= baud_cnt + '1';
                                                end if;
                                                state_rx <= st_bit;
                                            end if;
                            when st_stop => if baud_cnt < 15 then
                                                state_rx <=st_stop;
                                                baud_cnt <= baud_cnt + '1';
                                            elsif baud_cnt = 15 then
                                                state_rx <= st_wait;
                                                baud_cnt <= "0000";    
                                            end if;
                        end case;
                    end if;
                    
        end if;
    end process;
    
    process(state_rx)
        begin
            case state_rx is
                when st_idle => RX_RDY<='0';
                when st_start => RX_RDY<='0';
                when st_bit => RX_RDY<='0';
                when st_stop => RX_RDY<='0';
                when st_wait => RX_RDY <= '1';
             end case;
     end process;
    
    process(clk)
    begin
        if rising_edge(clk) then
         baud_count1<=baud_count1+1;
            if baud_count1= 651 then
                baud_en1<='1';
                baud_count1<="00000000000";
            else 
                baud_en1<='0';
            end if;
        end if;
       
    end process;
   
    
 
    

    --FSM lab11
    
    txd<=tx;
    
    --rst<=dbtn(2);
    
    
    --tx_data<=sw(7 downto 0);
    
    process(tf)
    begin
        case tf is
            when "00" => tx_data1<=("0000" & rom_data(15 downto 12)) ;
            when "01" => tx_data1 <= ("0000" & rom_data(11 downto 8)) ;
            when "10" => tx_data1 <= ("0000" & rom_data(7 downto 4)) ;
            when others => tx_data1 <=("0000" & rom_data(3 downto 0)) ;
    end case;
    end process;
    
    --ASCII Conv
    
    process(tx_data1)
    begin
        case tx_data1 is
            when B"0000_0000" => tx_data <= "00110000";
            when B"0000_0001" => tx_data <= "00110001";
            when B"0000_0010" => tx_data <= "00110010";
            when B"0000_0011" => tx_data <= "00110011";
            when B"0000_0100" => tx_data <= "00110100"; 
            when B"0000_0101" => tx_data <= "00110101";
            when B"0000_0110" => tx_data <= "00110110";
            when B"0000_0111" => tx_data <= "00110111";
            when B"0000_1000" => tx_data <= "00111000";
            when B"0000_1001" => tx_data <= "00111001";
            when B"0000_1010" => tx_data <= "01000001";
            when B"0000_1011" => tx_data <= "01000010";
            when B"0000_1100" => tx_data <= "01000011";
            when B"0000_1101" => tx_data <= "01000100";
            when B"0000_1110" => tx_data <= "01000101";
            when B"0000_1111" => tx_data <= "01000110";
            when others => tx_data <= "01010000";
        end case;    
    end process;
    
    --FSM process 1
    
    process(baud_en,rst, tx_en, clk)
    begin
       if (dbtn(2) ='1') then
            state <=st_idle;
           elsif baud_en='1' then
            if rising_edge(clk) then
        case state is
            when st_idle => if tx_en= '1' then
                                state <= st_start;
                             end if;
                             bit_cnt <= "000";
                             
            when st_start => state <= st_bit;
            
           when st_bit => if(bit_cnt<7) then
                                            state<=st_bit;
                                         elsif bit_cnt=7 then
                                            state<=st_stop;
                                          end if;
                                          bit_cnt <=bit_cnt+1;
                                          
          when st_stop => state <= st_idle;
                               
             
        end case;
        end if;
       end if;  
    end process;
    
    --FSM process 2
    
    process(state)
    begin
        case state is
            when st_idle => TX<='1';
                            TX_RDY<='0';
                            --tf<="00";
            when st_start => TX<='0';
                             TX_RDY<='0';
            when st_bit => TX<= TX_DATA(conv_integer(bit_cnt));
                            TX_RDY<='0';
            when st_stop => TX<='1';
                            TX_RDY<='1';
                            --tf<=tf+1;
         end case;
                            
    end process;
    
  --tx enable ff
    
    process(clk,tx_rdy)
    begin
        if rising_edge(clk) then
            if tx_cnt = "10" and tf = 3 then
                tx_en<='0';
                --tf<="00";
            elsif dbtn(4) = '1' then
                tx_en <= '1';
                --tf<= tf+1;
            end if;
        end if;    
    end process;
    
    process(clk,tx_rdy)
    begin
        if rising_edge(clk) then
            tx_cnt <= tx_rdy & tx_cnt(1);
        end if;
    end process;
    
    process(tx_cnt,clk)
    begin
        if rising_edge(clk) then
            if(tx_cnt = "10") then
                if(tf = "11") then
                    tf<="00";
                else
                    tf<=tf+1;
                end if;
            end if;
        end if;   
    end process;
    
    --baud
    
    process(clk)
    begin
        if rising_edge(clk) then
         baud_count<=baud_count+1;
            if baud_count=10415 then
                baud_en<='1';
                baud_count<=x"0000";
            else 
                baud_en<='0';
            end if;
        end if;
       
    end process;
   
    --PC 
    process(clk)
    begin
        if dbtn(1) = '1' then
            cnt<=x"0000";
        end if;
        if clk'event and clk = '1' then          
          if dbtn(3) = '1' then
            if (branch and z)= '1' then
                cnt<=branch_addr;
                elsif jump='1' then
                    cnt<=jmp_addr;
                else
                    cnt<=cnt+1;
                end if;
          end if;
        end if;
    end process;
    
  --ROM, Instr mem
    rom_data<=rom(conv_integer(cnt(7 downto 0)));
    
  --RF
    
    ra1<=rom_data(12 downto 10); --rs
    ra2<=rom_data(9 downto 7); -- rt
    -- wa<=cnt(3 downto 0);
    wa<=rom_data(9 downto 7) when RegDst='0' else rom_data(6 downto 4); -- mux rd, rt
   
   -- wd<=rd1+rd2;
    
    rd1<=reg(conv_integer(ra1));
    rd2<=reg(conv_integer(ra2));
    
   
    
    process(cnt, clk)
    begin
        if rising_edge(clk) then
            if (rwrite and dbtn(3))='1' then
                reg(conv_integer(wa))<=wd;
                
            end if;
        end if;           
    end process;   
    
  --ram
    
    ram_data_in<=rd2;
    
    ram_out<=ram(conv_integer(alurez));
    --ram read
    
    process(cnt, clk)
    begin
        if rising_edge(clk) then
            if Memwr = '1' then
                ram(conv_integer(alurez))<=ram_data_in;
            end if;
        end if;
        
    end process;
    
    wd<=ram_out when (memtoreg = '1') else alurez;
    
    --Main Control
    
   
    
    process(rom_data)
    begin
        case rom_data(15 downto 13) is
            when "000" => RegDst <='1'; --rtype
                          rwrite <= '1';
                          branch<='0';
                          alusrc<='0';
                          jump<='0';
                          memread<='0';
                          memwr<='0';
                          memtoreg<='0';
                          aluop<="000";
            when "001" => RegDst <='0'; --addi
                          rwrite <= '1';
                          branch<='0';
                          alusrc<='1';
                          jump<='0';
                          memread<='0';
                          memwr<='0';
                          memtoreg<='0';
                          aluop<="001";
            when "010" => RegDst <='0'; -- lw
                         rwrite <= '1';
                         branch<='0';
                         alusrc<='1';
                         jump<='0';
                         memread<='1';
                         memwr<='0';
                         memtoreg<='1';
                         aluop<="001";
            when "011" => RegDst <='0'; -- sw
                         rwrite <= '0';
                         branch<='0';
                         alusrc<='1';
                         jump<='0';
                         memread<='0';
                         memwr<='1';
                         memtoreg<='0';
                         aluop<="001";                                                                          
             when "100" => RegDst <='0'; -- beq
                           rwrite <= '0';
                           branch<='1';
                           alusrc<='0';
                           jump<='0';
                           memread<='0';
                           memwr<='0';
                           memtoreg<='0';
                           aluop<="010";
             when "101" => RegDst <='0'; --  xori
                           rwrite <= '1';
                           branch<='0';
                           alusrc<='1';
                           jump<='0';
                           memread<='0';
                           memwr<='0';
                           memtoreg<='0';
                           aluop<="100";
            when "110" => RegDst <='0'; -- ori
                          rwrite <= '1';
                          branch<='0';
                          alusrc<='1';
                          jump<='0';
                          memread<='0';
                          memwr<='0';
                          memtoreg<='0';
                          aluop<="011";
            when others => RegDst <='0'; -- jmp
                          rwrite <= '0';
                          branch<='0';
                          alusrc<='0';
                          jump<='1';
                          memread<='0';
                          memwr<='0';
                          memtoreg<='0'; 
          end case;             
                  
                                                 
    end process;
    
   --jump
   -- jmp_addr<="000"&rom_data(12 downto 0);
    
   
    
    --sign ext
    ext<="100000000"&rom_data(6 downto 0) when ExtOp='1' else "000000000"&rom_data(6 downto 0);
    
    
    --afis
    
    
    process(sw(15 downto 13))
    begin
      case sw(15 downto 13) is
           when "000" => rez <= rom_data;
           when "001" => rez <=cnt;
           --when "010" => rez <= rd1;
           when "010" => rez <= "00000000" & rx_data;
           when "011" => rez <= rd2;
           when "100" => rez <= ext;
           when "101" => rez <= alurez;
           when "110" => rez <= ram_out;
           when others=> rez <= wd; 
         end case;
     end process;
    
    
    
    --a<="000000000000"&sw(3 downto 0);
   -- b<="000000000000"&sw(7 downto 4);
   -- c<="00000000"&sw(7 downto 0);
    
    --ALU
    
    b<= rd2 when (alusrc='0') else ext; --MUX ALUSrc
    
    process(rom_data(2 downto 0), aluop)
    begin
        case aluop is
            when "000" => aluctrl <=rom_data(2 downto 0);
            when "001" => aluctrl <="000";
            when "010" => aluctrl <="001";
            when "011" => aluctrl <="101";
            when others=> aluctrl <="111";
         end case;
    end process;    
    
    b<= rd2 when (alusrc='0') else ext; --MUX ALUSrc
    a<= rd1;
    sa<=rom_data(3);
    
    process(aluctrl)
   begin
       case aluctrl is
           when "000" => alurez <= a+b;
           when "001" => alurez <= a-b;
                         if alurez = x"0000" then
                            z<='1';
                         else z<='0';
                         end if;
           when "010" => if sa='1' then alurez<= a(14 downto 0) & '0';
                         else alurez<=a;
                         end if;
           when "011" => if sa='1' then alurez<= '0' & a(15 downto 1);
                         else alurez<=a;
                         end if;
           when "100" => alurez <= a and b;
           when "101" => alurez <= a or b;
           when "110" => alurez <= a nand b;
           when others => alurez <= a xor b;
           end case;
   end process;
    
    jmp_addr<="000"& rom_data(12 downto 0);
    branch_addr<=(cnt+1)+ext;

   led(0)<=memtoreg;
 
   -- an <= btn(3 downto 0);
   -- cat <= (others=>'0');


end Behavioral;
