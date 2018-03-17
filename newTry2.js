
prog -> classDeclRepeat funcDefRepeat 'program' funcBody ';'
classDecl -> 'class' 'id' classDeclOptionRepeat '{' varDeclRepeatfuncDeclRepeat '}' ';'

classDeclBracketRepeat -> ',' 'id' classDeclBracketRepeat | EPSILON
classDeclOptionRepeat -> ':' 'id' classDeclBracketRepeat | EPSILON



funcDecl -> type 'id' '(' fParams ')' ';'
funcHead -> type ['id' 'sr'] 'id' '(' fParams ')'
funcDef -> funcHead funcBody ';'
funcBody -> '{' varDeclRepeatstatementRepeat '}'

varDeclRepeatstatementRepeat -> type 'id' varDeclRepeat2 varDeclRepeatstatementRepeat2 | EPSILON
statementRepeat -> statement statementRepeat | EPSILON

varDeclRepeatstatementRepeat2 -> statementRepeat

varDeclRepeatfuncDeclRepeat -> type 'id' varDeclRepeat2 varDeclRepeatfuncDeclRepeat2| EPSILON

varDeclRepeatfuncDeclRepeat2 -> type 'id' funcDeclRepeat2 | EPSILON

funcDeclRepeat2 ->  '(' fParams ')' ';' type 'id' funcDeclRepeat2 |  '(' fParams ')' ';'

varDeclRepeat2 ->   type 'id' varDeclRepeat2 |  arraySizeRepeat ';'

statement -> assignStat ';'
 | 'if' '(' expr ')' 'then' statBlock 'else' statBlock ';'
 | 'for' '(' type 'id' assignOp expr ';' arithExpr relOp arithExpr ';' assignStat ')' statBlock ';'
 | 'get' '(' variable ')' ';'
 | 'put' '(' expr ')' ';'
 | 'return' '(' expr ')' ';'
assignStat -> idnestRepeat 'id' variable assignOp expr
statBlock -> '{' statementRepeat '}' | statement | EPSILON
expr -> arithExpr relExpr
relExpr -> relOp arithExpr | EPSILON
arithExpr ->  term arithExprTail
arithExprTail -> addOp term arithExprTail | EPSILON
sign -> '+' | '-'
term -> factor termTail
termTail -> multOp factor termTail | EPSILON
factor -> factorFactor
 | 'intNum' | 'floatNum'
 | '(' arithExpr ')'
 | 'not' factor
 | sign factor
factorFactor -> 'id' idnestRepeat 'id' variableOrfunctionCall
idnestRepeat ->  idnestFactor 'id' idnestRepeat | 'id' idnestFactor

idnestFactor -> indiceRepeat '.'
  | '(' aParams ')' '.'


variableOrfunctionCall -> variable | functionCall
variable ->  indiceRepeat
functionCall ->  '(' aParams ')'

indice -> '[' arithExpr ']'
arraySize -> '[' 'intNum' ']'
type -> 'int' | 'float' | 'id'
fParams -> type 'id' arraySizeRepeat fParamsTailRepeat | EPSILON
aParams -> expr aParamsTailRepeat | EPSILON
fParamsTail -> ',' type 'id' arraySizeRepeat
aParamsTail -> ',' expr
assignOp -> '='
relOp -> 'eq' | 'neq' | 'lt' | 'gt' | 'leq' | 'geq'
addOp -> '+' | '-' | 'or'
multOp -> '*' | '/' | 'and'
classDeclRepeat ->  classDecl classDeclRepeat | EPSILON
funcDefRepeat -> funcDef funcDefRepeat | EPSILON

indiceRepeat -> indice indiceRepeat | EPSILON
aParamsTailRepeat -> aParamsTail aParamsTailRepeat | EPSILON
arraySizeRepeat -> arraySize arraySizeRepeat | EPSILON
fParamsTailRepeat -> fParamsTail fParamsTailRepeat  | EPSILON
