local i <close> = assert(io.open('lenta-ru-news.csv'))
local o <close> = assert(io.open('lenta-ru-news-tagged.csv', 'wb'))

for l in i:lines() do
  l = l:gsub('""', '')
  local content, tags = l:match '.-,(.*),([^,]-,[^,]-),%d%d%d%d/%d%d/%d%d'

  local body
  assert(content, l)
  if content:sub(1, 1) == '"' then
    body = content:match '".-",(.*)'
  else
    body = content:match '.-,(.*)'
  end

  o:write(body, ',', tags, '\n')
end