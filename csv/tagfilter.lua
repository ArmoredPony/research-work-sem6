local TAG <const> = 'Спорт'
local FILEEXT <const> = 'sport'

local i <close> = assert(io.open('lenta-ru-news-tagged.csv'))
local o <close> = assert(io.open(FILEEXT..'/lenta-ru-'..FILEEXT..'.csv', 'w'))

for l in i:lines() do
  local content, tag1, tag2 = l:match '(.*),(.-),(.-)$'
  if tag1 == TAG or tag2 == TAG then o:write(content, '\n') end
end