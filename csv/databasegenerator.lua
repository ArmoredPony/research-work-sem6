local COUNT = 100000

local vec = require 'vectorizer'
local TYPES <const> = {'sport', 'science'}

local database <close> = assert(io.open('database-'..(COUNT or 'full')..'-count.csv', 'w'))
COUNT = COUNT and COUNT / #TYPES
for _, type in ipairs(TYPES) do
  local r = 0
  local data <close> = assert(io.open(type..'/lenta-ru-'..type..'.csv'))
  for line in data:lines() do
    if COUNT and r >= COUNT then break end
    database:write(table.concat(vec(line, type), ','), '\n')
    r = r + 1
  end
end