from pathlib import Path
import unittest

import yaml


GITHUB = Path(__file__).resolve().parents[1] / ".github"


class SupplyChainTests(unittest.TestCase):
    def test_external_workflow_references_are_immutable(self):
        for workflow in sorted((GITHUB / "workflows").iterdir()):
            if workflow.suffix not in {".yaml", ".yml"}:
                continue
            jobs = yaml.safe_load(workflow.read_text())["jobs"]
            for job_name, job in jobs.items():
                for item in [job, *job.get("steps", [])]:
                    ref = item.get("uses")
                    if ref is None or ref.startswith("./"):
                        continue
                    with self.subTest(workflow=workflow.name, job=job_name, ref=ref):
                        if ref.startswith("docker://"):
                            self.assertRegex(ref, r"@sha256:[0-9a-f]{64}$")
                        else:
                            self.assertRegex(ref, r"@[0-9a-f]{40}$")

    def test_dependabot_covers_project_dependencies(self):
        config = yaml.safe_load((GITHUB / "dependabot.yml").read_text())
        self.assertEqual(2, config["version"])
        coverage = set()
        for update in config["updates"]:
            directories = update.get("directories") or [update["directory"]]
            coverage.update((update["package-ecosystem"], path) for path in directories)
        expected = {("github-actions", "/"), ("maven", "/Backend"),
                    ("docker", "/Backend"), ("docker", "/deploy")}
        self.assertFalse(expected - coverage, f"Missing Dependabot entries: {expected - coverage}")
