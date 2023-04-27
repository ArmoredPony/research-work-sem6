local VECTORS <const> = 100

local science <close> = assert(io.open('science/science-database.csv'))
local sport <close> = assert(io.open('sport/sport-database.csv'))
local database <close> = assert(io.open('database.csv', 'w'))

for _ = 1, VECTORS / 2 do
  local sciencevec = science:read 'l'
  local sportvec = sport:read 'l'
  database:write(sciencevec, '\n', sportvec, '\n')
end