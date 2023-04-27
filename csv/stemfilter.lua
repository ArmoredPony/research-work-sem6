local FILEEXT <const> = 'science'

local utf = require 'lua-utf8'
local i <close> = assert(io.open(FILEEXT..'-stemmed.csv'))
local o <close> = assert(io.open(FILEEXT..'-filtered.csv', 'w'))
local stopwords_filter <close> = assert(io.open(
  'C:/Users/raxme/Documents/CodeProjects/research_work/semester5/v3/csv/filters/stopwords.filter'))
local names_filter <close> = assert(io.open(
  'C:/Users/raxme/Documents/CodeProjects/research_work/semester5/v3/csv/filters/names.filter'))
print 'files are opened'

local stopwords, swcount = {}, 0
local names, ncount = {}, 0
for l in stopwords_filter:lines() do
  stopwords[l] = true
  swcount = swcount + 1
end
for l in names_filter:lines() do
  names[l] = true
  ncount = ncount + 1
end
print('filters are loaded: '..swcount..' stopwords, '..ncount..' names')

for l in i:lines() do
  if l:find('??', nil, true) then goto continue end
  l = l:match '[^|]+'
  if utf.len(l) > 3 and not (stopwords[l] or names[l]) then
    o:write(l, '\n')
  end
  ::continue::
end