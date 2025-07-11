{{- define "pino-db-idp.name" -}}
{{- .Chart.Name | trunc 63 | trimSuffix "-" -}}
{{- end }}

{{- define "pino-db-idp.fullname" -}}
{{- printf "%s" .Release.Name -}}
{{- end }}

{{- define "pino-db-idp.chart" -}}
pino-db-idp
{{- end }}

{{- define "pino-db-idp.labels" -}}
helm.sh/chart: {{ .Chart.Name }}-{{ .Chart.Version }}
app.kubernetes.io/name: {{ .Chart.Name }}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/version: {{ .Chart.AppVersion }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}
