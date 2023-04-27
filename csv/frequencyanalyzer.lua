local TYPE <const> = 'science'
local LIMIT <const> = 50000

local stem = require 'stemmer'
local dict = require 'dictionary'

local data <close> = assert(io.open(TYPE..'/lenta-ru-'..TYPE..'.csv'))
local linecount, errors = 0, 0
for line in data:lines() do
  local typecount = {science = 0, sport = 0}
  local tokens = stem(line)
  for token in pairs(tokens) do
    local type = dict[token]
    if type then typecount[type] = typecount[type] + 1 end
  end

  local maxtype, count = nil, 0
  for type, c in pairs(typecount) do
    if c > count then
      count = c
      maxtype = type
    end
  end

  if maxtype ~= TYPE then
    errors = errors + 1
  end
  linecount = linecount + 1
  if linecount % 1000 == 0 then print('processed: '..linecount) end
  if linecount == LIMIT then break end
end
print(TYPE..':\t'..linecount,
      'errors:\t'..errors,
      'error%:\t'..(errors / linecount * 100))