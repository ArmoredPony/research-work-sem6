local EXT1 <const>, EXT2 <const> = 'sport', 'science'

local dict1 = {}
assert(loadfile(EXT1..'/'..EXT1..'-dictionary.lua', 't', dict1))()

local dict2 = {}
assert(loadfile(EXT2..'/'..EXT2..'-dictionary.lua', 't', dict2))()
for word, category in pairs(dict2) do
  if dict1[word] then
    dict1[word] = nil
  else
    dict1[word] = category
  end
end

local o <close> = assert(io.open('dictionary.lua', 'w'))
o:write('return {\n')
for word, category in pairs(dict1) do o:write("['", word, "'] = '", category, "',\n") end
o:write('}')