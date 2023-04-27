local utf = require 'lua-utf8'
local STOPWORDS = {}
assert(loadfile('filters/names.lua', 't', STOPWORDS))()
assert(loadfile('filters/stopwords.lua', 't', STOPWORDS))()

---Stemmificates given `text` and returns a bag of tokens.
---@param text string
---@return table<string, true>
return function (text)
  local mystem <close> = assert(io.popen('echo '..text..' | mystem.exe -nwl'))
  local tokens = {}
  for token in mystem:lines() do
    token = token:match('^[^|]+')
    if utf.len(token) > 3 and not (STOPWORDS[token] or token:find('??', nil, true)) then
      tokens[token] = tokens[token] and tokens[token] + 1 or 1
    end
  end
  return tokens
end