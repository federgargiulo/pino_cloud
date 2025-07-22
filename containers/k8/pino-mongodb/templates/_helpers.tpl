{{- define "pino-mongodb.name" -}}
pino-mongodb
{{- end -}}

{{- define "pino-mongodb.fullname" -}}
{{ include "pino-mongodb.name" . }}-{{ .Release.Name }}
{{- end -}}
