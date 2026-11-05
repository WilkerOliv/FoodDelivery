"""Read feature availability without enabling subscriptions or changing repository visibility."""
import os
import urllib.error
import urllib.request


def available(repository, token, opener=urllib.request.urlopen):
    request = urllib.request.Request(
        'https://api.github.com/repos/' + repository + '/code-scanning/analyses?per_page=1',
        headers={'Authorization': 'Bearer ' + token, 'Accept': 'application/vnd.github+json',
                 'X-GitHub-Api-Version': '2022-11-28'})
    try:
        with opener(request, timeout=20) as response:
            return response.status == 200
    except urllib.error.HTTPError as error:
        if error.code in (403, 404):
            return False
        raise


def main():
    enabled = available(os.environ['GITHUB_REPOSITORY'], os.environ['GH_TOKEN'])
    enabled = enabled and os.environ.get('CODEQL_ENABLED') != 'false'
    with open(os.environ['GITHUB_OUTPUT'], 'a') as output:
        output.write('codeql=' + str(enabled).lower() + '\n')
    with open(os.environ['GITHUB_STEP_SUMMARY'], 'a') as summary:
        summary.write('## Security checks\n\nSemgrep Community Edition and the backend SBOM run without a paid security subscription.\n\n')
        if enabled:
            summary.write('The GitHub API permits Code Scanning for this repository; CodeQL will run.\n')
        else:
            summary.write('CodeQL will not run: Code Scanning is unavailable to this workflow or explicitly disabled. Keep the repository private; no subscription or visibility change is required for the free Semgrep check. Semgrep does not replace CodeQL or a complete security review.\n')


if __name__ == '__main__':
    main()
