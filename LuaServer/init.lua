local SSID = "NodeMCU"
local SSID_PASSWORD = "12345678"
 
local function connect (conn, data)
 
   conn:on ("receive",
      function (cn, req_data)
        local query_data = get_http_req (req_data)
        --print (query_data["METHOD"] .. " " .. " " .. query_data["User-Agent"])
        if query_data["matrixValues"] ~= nil then
           uart.write(1, query_data["matrixValues"])
        end
        --print("Send response")
        --[[cn:send('HTTP/1.1 200 OK\n\n', 
        function()
            print("close cn ")
            cn:close()
        end)--]]
        print("Close cn")
        cn:close()
      end)
end
 
-- Build and return a table of the http request data
function get_http_req (instr)
   local t = {}
   local first = nil
   local key, v, strt_ndx, end_ndx
 
   for str in string.gmatch (instr, "([^\n]+)") do
      -- First line in the method and path
      if (first == nil) then
         first = 1
         strt_ndx, end_ndx = string.find (str, "([^ ]+)")
         v = trim (string.sub (str, end_ndx + 2))
         key = trim (string.sub (str, strt_ndx, end_ndx))
         t["METHOD"] = key
         t["REQUEST"] = v
      else -- Process and reamaining ":" fields
         strt_ndx, end_ndx = string.find (str, "([^:]+)")
         if (end_ndx ~= nil) then
            v = trim (string.sub (str, end_ndx + 2))
            key = trim (string.sub (str, strt_ndx, end_ndx))
            t[key] = v
         end
      end
   end
   return t
end
 
-- String trim left and right
function trim (s)
  return (s:gsub ("^%s*(.-)%s*$", "%1"))
end
 
-- Configure the ESP as a station (client)
wifi.setmode (wifi.SOFTAP)

cfg={}
cfg.ssid=SSID
cfg.pwd=SSID_PASSWORD
wifi.ap.config(cfg)

cfg={}
cfg.ip="192.168.1.1";
cfg.netmask="255.255.255.0";
cfg.gateway="192.168.1.1";
wifi.ap.setip(cfg)

print("Set up UART config")
uart.setup(1, 921600, 8, uart.PARITY_NONE, uart.STOPBITS_1, 1)
--while 1 do
--    uart.write(1, 241)
--    tmr.delay(1000000)
--end
-- Create the httpd server
svr = net.createServer (net.TCP, 30)
 
-- Server listening on port 80, call connect function if a request is received
svr:listen (80, connect)
