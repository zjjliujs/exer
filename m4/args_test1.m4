define(`macro', `$1')
macro( unquoted leading space lost)
macro(` quoted leading space kept')
macro(
divert `unquoted space kept after expansion')
macro(macro(`
')`whitespace from expansion kept')
macro(`unquoted trailing whitespace kept'
)

