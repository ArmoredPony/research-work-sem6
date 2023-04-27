local FILEEXT <const> = 'sport'
local COUNT <const> = 100

local dict <close> = assert(io.open(FILEEXT..'-dictionary.lua', 'w'))
local filtered <close> = assert(io.open(FILEEXT..'-filtered.csv'))
local wbag = {}
for word in filtered:lines() do
  local wcount = wbag[word]
  if wcount then
    wbag[word] = wcount + 1
  else
    wbag[word] = 1
  end
end

local t, i = {}, 1
for w, c in pairs(wbag) do
  t[i] = {w, c}
  i = i + 1
end
table.sort(t, function (a, b) return a[2] > b[2] end)

i = COUNT
for _, pair in ipairs(t) do
  dict:write('_ENV["'..pair[1]..'"]=true', '\n')
  if i == 0 then break end
  i = i - 1
end